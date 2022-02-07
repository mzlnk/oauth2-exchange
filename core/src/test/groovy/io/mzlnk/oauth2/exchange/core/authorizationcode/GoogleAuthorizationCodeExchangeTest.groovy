package io.mzlnk.oauth2.exchange.core.authorizationcode

import io.mzlnk.oauth2.exchange.core.ExchangeException
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.GoogleAuthorizationCodeExchangeClient
import io.mzlnk.oauth2.exchange.core.utils.http.MockHttpClientInterceptor
import okhttp3.OkHttpClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

import static io.mzlnk.oauth2.exchange.core.utils.TestUtils.loadResourceAsString
import static io.mzlnk.oauth2.exchange.core.utils.http.HttpFixtures.*
import static io.mzlnk.oauth2.exchange.core.utils.http.rule.HttpRules.post
import static io.mzlnk.oauth2.exchange.core.utils.http.rule.HttpRules.withExactFormBody
import static org.junit.jupiter.api.Assertions.assertThrows

class GoogleAuthorizationCodeExchangeTest {

    private static String BASE_PATH = 'io/mzlnk/oauth2/exchange/core/authorizationcode/GoogleAuthorizationCodeExchangeTest'

    private GoogleAuthorizationCodeExchange exchange
    private MockHttpClientInterceptor http

    @BeforeEach
    void "Set up tests"() {
        this.http = new MockHttpClientInterceptor()

        def httpClient = new OkHttpClient.Builder()
                .addInterceptor(http)
                .build()

        def exchangeClient = new GoogleAuthorizationCodeExchangeClient(
                'some-client-id',
                'some-client-secret',
                'some-redirect-uri'
        )

        this.exchange = new GoogleAuthorizationCodeExchange.Builder()
                .httpClient(httpClient)
                .exchangeClient(exchangeClient)
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
                post('https://oauth2.googleapis.com/token'),
                withExactFormBody([
                        client_id    : 'some-client-id',
                        client_secret: 'some-client-secret',
                        code         : 'some-code',
                        grant_type   : 'authorization_code',
                        redirect_uri : 'some-redirect-uri'
                ]),
        ).thenReturn(httpResponse)

        when:
        def response = this.exchange.exchangeAuthorizationCode('some-code')

        then:
        assert response != null
        assert response.getAccessToken() == 'some-access-token'
        assert response.getExpiresIn() == 5000
        assert response.getRefreshToken() == 'some-refresh-token'
        assert response.getScope() == 'some-scope'
        assert response.getTokenType() == 'Bearer'
    }

    @Test
    void "Should return exception when exchange failed"() {
        given:
        def json = loadResourceAsString("${BASE_PATH}/invalid-client-response.json")
        def httpResponse = defaultBadRequestHttpResponse() {
            body(jsonResponseBody(json))
        }

        this.http.when(
                post('https://oauth2.googleapis.com/token'),
                withExactFormBody([
                        client_id    : 'some-client-id',
                        client_secret: 'some-client-secret',
                        code         : 'some-code',
                        grant_type   : 'authorization_code',
                        redirect_uri : 'some-redirect-uri'
                ]),
        ).thenReturn(httpResponse)

        when:
        def exception = assertThrows(ExchangeException, () -> this.exchange.exchangeAuthorizationCode('some-code'))

        then:
        assert exception != null
        assert exception.getMessage() == 'Exchange failed. Cause: invalid_client, Unauthorized'
    }

    @Test
    void "Should return exception when exchange does not return 2xx or 4xx response"() {
        given:
        def httpResponse = defaultInternalServerErrorHttpResponse()

        this.http.when(
                post('https://oauth2.googleapis.com/token'),
                withExactFormBody([
                        client_id    : 'some-client-id',
                        client_secret: 'some-client-secret',
                        code         : 'some-code',
                        grant_type   : 'authorization_code',
                        redirect_uri : 'some-redirect-uri'
                ]),
        ).thenReturn(httpResponse)

        when:
        def exception = assertThrows(ExchangeException, () -> this.exchange.exchangeAuthorizationCode('some-code'))

        then:
        assert exception != null
        assert exception.getMessage() == 'Exchange failed. Cause: Internal Server Error'
    }

    @Test
    @Disabled('Disabled until GH-30 done')
    // TODO: GH-30
    void "Should return exception when provide empty authorization code"() {
        given:
        def emptyCode = ''

        when:
        def exception = assertThrows(IllegalArgumentException, () -> this.exchange.exchangeAuthorizationCode(emptyCode))

        then:
        assert exception != null
        assert exception.getMessage() == 'Authorization code cannot be empty'
    }

    @Test
    @Disabled('Disabled until GH-30 done')
    // TODO: GH-30
    void "Should return exception when provide null authorization code"() {
        given:
        def nullCode = null

        when:
        def exception = assertThrows(IllegalArgumentException, () -> this.exchange.exchangeAuthorizationCode(nullCode))

        then:
        assert exception != null
        assert exception.getMessage() == 'Authorization code cannot be null'
    }

    @Test
    @Disabled('Disabled until GH-30 done')
    // TODO: GH-30
    void "Should not create exchange when provide null exchange client"() {
        given:
        def exchangeBuilder = new GoogleAuthorizationCodeExchange.Builder()
                .httpClient(new OkHttpClient())

        when:
        def exception = assertThrows(IllegalArgumentException, () -> exchangeBuilder.build())

        then:
        assert exception != null
        assert exception.getMessage() == 'Exchange client cannot be null'
    }

}
