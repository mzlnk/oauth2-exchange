package io.mzlnk.oauth2.exchange.core.authorizationcode

import com.fasterxml.jackson.databind.ObjectMapper
import io.mzlnk.oauth2.exchange.core.ExchangeException
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.KeycloakOAuth2Client
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.KeycloakOAuth2TokenResponseHandler
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.KeycloakOAuth2TokenResponse
import io.mzlnk.oauth2.exchange.core.utils.http.MockHttpClientInterceptor
import okhttp3.OkHttpClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static io.mzlnk.oauth2.exchange.core.utils.TestUtils.loadResourceAsString
import static io.mzlnk.oauth2.exchange.core.utils.http.HttpFixtures.*
import static io.mzlnk.oauth2.exchange.core.utils.http.rule.HttpRules.post
import static io.mzlnk.oauth2.exchange.core.utils.http.rule.HttpRules.withExactFormBody
import static org.junit.jupiter.api.Assertions.assertThrows

class KeycloakAuthorizationCodeExchangeSystemTest {

    private static String BASE_PATH = 'io/mzlnk/oauth2/exchange/core/authorizationcode/KeycloakAuthorizationCodeExchangeSystemTest'

    private KeycloakAuthorizationCodeExchange exchange
    private MockHttpClientInterceptor http

    @BeforeEach
    void "Set up tests"() {
        this.http = new MockHttpClientInterceptor()

        def httpClient = new OkHttpClient.Builder()
                .addInterceptor(this.http)
                .build()

        def exchangeClient = new KeycloakOAuth2Client(
                'some-client-id',
                'some-client-secret',
                'some-redirect-uri',
                'https://some.domain.com',
                'some-realm'
        )

        def responseHandler = new KeycloakOAuth2TokenResponseHandler(new ObjectMapper())

        this.exchange = KeycloakAuthorizationCodeExchange.builder()
                .httpClient(httpClient)
                .oAuth2Client(exchangeClient)
                .responseHandler(responseHandler)
                .build()
    }

    @Test
    void "Should return token response"() {
        given:
        def json = loadResourceAsString("${BASE_PATH}/success-response.json")
        def httpResponse = defaultSuccessHttpResponse() {
            body(jsonResponseBody(json))
        }

        this.http.when(
                post('https://some.domain.com/auth/realms/some-realm/protocol/openid-connect/token'),
                withExactFormBody([
                        client_id    : 'some-client-id',
                        client_secret: 'some-client-secret',
                        code         : 'some-code',
                        grant_type   : 'authorization_code',
                        redirect_uri : 'some-redirect-uri'
                ])
        ).thenReturn(httpResponse)

        when:
        def response = this.exchange.exchangeAuthorizationCode('some-code') as KeycloakOAuth2TokenResponse

        then:
        assert response != null
        assert response.getAccessToken() == 'some-access-token'
        assert response.getExpiresIn() == 300
        assert response.getRefreshExpiresIn() == 1800
        assert response.getRefreshToken() == 'some-refresh-token'
        assert response.getTokenType() == 'bearer'
        assert response.getNotBeforePolicy() == 0
        assert response.getSessionState() == 'some-session-state'
        assert response.getScope() == 'some-scope'
    }

    @Test
    void "Should return exception when exchange returns bad request response"() {
        given:
        def json = loadResourceAsString("${BASE_PATH}/error-response-invalid-request.json")
        def httpResponse = defaultBadRequestHttpResponse() {
            body(jsonResponseBody(json))
        }

        this.http.when(
                post('https://some.domain.com/auth/realms/some-realm/protocol/openid-connect/token'),
                withExactFormBody([
                        client_id    : 'some-client-id',
                        client_secret: 'some-client-secret',
                        code         : 'some-code',
                        grant_type   : 'authorization_code',
                        redirect_uri : 'some-redirect-uri'
                ])
        ).thenReturn(httpResponse)

        when:
        def exception = assertThrows(
                ExchangeException,
                () -> this.exchange.exchangeAuthorizationCode('some-code')
        )

        then:
        assert exception != null
        assert exception.getMessage() == 'Exchange failed. Cause: Bad Request - invalid_request'
    }

    @Test
    void "Should return exception when exchange returns error response other than bad request one"() {
        given:
        def httpResponse = defaultInternalServerErrorHttpResponse()

        this.http.when(
                post('https://some.domain.com/auth/realms/some-realm/protocol/openid-connect/token'),
                withExactFormBody([
                        client_id    : 'some-client-id',
                        client_secret: 'some-client-secret',
                        code         : 'some-code',
                        grant_type   : 'authorization_code',
                        redirect_uri : 'some-redirect-uri'
                ])
        ).thenReturn(httpResponse)

        when:
        def exception = assertThrows(
                ExchangeException,
                () -> this.exchange.exchangeAuthorizationCode('some-code')
        )

        then:
        assert exception != null
        assert exception.getMessage() == 'Exchange failed. Cause: Internal Server Error'
    }

    @Test
    void "Should return exception when provide empty authorization code"() {
        given:
        def emptyCode = ''

        when:
        def exception = assertThrows(
                IllegalArgumentException,
                () -> this.exchange.exchangeAuthorizationCode(emptyCode)
        )

        then:
        assert exception != null
        assert exception.message == 'Authorization code cannot be empty'
    }

    @Test
    void "Should return exception when provide null authorization code"() {
        given:
        def nullCode = null

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> this.exchange.exchangeAuthorizationCode(nullCode)
        )

        then:
        assert exception != null
        assert exception.message == 'Authorization code cannot be null'
    }

    @Test
    void "Should return exception when provide no exchange client"() {
        given:
        def exchangeBuilder = KeycloakAuthorizationCodeExchange.builder()
                .httpClient(new OkHttpClient())

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> exchangeBuilder.build()
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `exchangeClient` cannot be null.'
    }

}
