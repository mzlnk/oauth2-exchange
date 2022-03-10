package io.mzlnk.oauth2.exchange.core.authorizationcode

import com.fasterxml.jackson.databind.ObjectMapper
import io.mzlnk.oauth2.exchange.core.ExchangeException
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.FacebookOAuth2Client
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.FacebookOAuth2TokenResponseHandler
import io.mzlnk.oauth2.exchange.core.utils.http.MockHttpClientInterceptor
import okhttp3.OkHttpClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static io.mzlnk.oauth2.exchange.core.utils.TestUtils.loadResourceAsString
import static io.mzlnk.oauth2.exchange.core.utils.http.HttpFixtures.*
import static io.mzlnk.oauth2.exchange.core.utils.http.rule.HttpRules.get
import static io.mzlnk.oauth2.exchange.core.utils.http.rule.HttpRules.withQueryParameter
import static org.junit.jupiter.api.Assertions.assertThrows

class FacebookAuthorizationCodeExchangeSystemTest {

    private static String BASE_PATH = 'io/mzlnk/oauth2/exchange/core/authorizationcode/FacebookAuthorizationCodeExchangeSystemTest'

    private FacebookAuthorizationCodeExchange exchange
    private MockHttpClientInterceptor http

    @BeforeEach
    void "Set up tests"() {
        this.http = new MockHttpClientInterceptor()

        def httpClient = new OkHttpClient.Builder()
                .addInterceptor(this.http)
                .build()

        def exchangeClient = new FacebookOAuth2Client(
                'some-client-id',
                'some-client-secret',
                'some-redirect-uri'
        )

        def responseHandler = new FacebookOAuth2TokenResponseHandler(new ObjectMapper())

        this.exchange = FacebookAuthorizationCodeExchange.builder()
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
                get('https://graph.facebook.com/v12.0/oauth/access_token'),
                withQueryParameter('client_id', 'some-client-id'),
                withQueryParameter('client_secret', 'some-client-secret'),
                withQueryParameter('redirect_uri', 'some-redirect-uri'),
                withQueryParameter('code', 'some-code')
        ).thenReturn(httpResponse)

        when:
        def response = this.exchange.exchangeAuthorizationCode('some-code')

        then:
        assert response != null
        assert response.getAccessToken() == 'some-access-token'
        assert response.getExpiresIn() == 5000
        assert response.getTokenType() == 'Bearer'
    }

    @Test
    void "Should return exception when exchange returns bad request response"() {
        given:
        def json = loadResourceAsString("${BASE_PATH}/error-response-invalid-client.json")
        def httpResponse = defaultBadRequestHttpResponse() {
            body(jsonResponseBody(json))
        }

        this.http.when(
                get('https://graph.facebook.com/v12.0/oauth/access_token'),
                withQueryParameter('client_id', 'some-client-id'),
                withQueryParameter('client_secret', 'some-client-secret'),
                withQueryParameter('redirect_uri', 'some-redirect-uri'),
                withQueryParameter('code', 'some-code')
        ).thenReturn(httpResponse)

        when:
        def exception = assertThrows(
                ExchangeException,
                () -> this.exchange.exchangeAuthorizationCode('some-code')
        )

        then:
        assert exception != null
        assert exception.message == 'Exchange failed. Cause: Bad Request - Missing or invalid client id.'
    }

    @Test
    void "Should return exception when exchange returns error response other than bad request"() {
        given:
        def httpResponse = defaultInternalServerErrorHttpResponse()

        this.http.when(
                get('https://graph.facebook.com/v12.0/oauth/access_token'),
                withQueryParameter('client_id', 'some-client-id'),
                withQueryParameter('client_secret', 'some-client-secret'),
                withQueryParameter('redirect_uri', 'some-redirect-uri'),
                withQueryParameter('code', 'some-code')
        ).thenReturn(httpResponse)

        when:
        def exception = assertThrows(
                ExchangeException,
                () -> this.exchange.exchangeAuthorizationCode('some-code')
        )

        then:
        assert exception != null
        assert exception.message == 'Exchange failed. Cause: Internal Server Error'
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
        def exchangeBuilder = FacebookAuthorizationCodeExchange.builder()
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
