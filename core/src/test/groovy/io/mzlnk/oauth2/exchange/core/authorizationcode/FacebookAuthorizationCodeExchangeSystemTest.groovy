package io.mzlnk.oauth2.exchange.core.authorizationcode

import com.fasterxml.jackson.databind.ObjectMapper
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.FacebookAuthorizationCodeExchangeClient
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.FacebookAuthorizationCodeExchangeResponseHandler
import io.mzlnk.oauth2.exchange.core.utils.http.MockHttpClientInterceptor
import okhttp3.OkHttpClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static io.mzlnk.oauth2.exchange.core.utils.TestUtils.loadResourceAsString
import static io.mzlnk.oauth2.exchange.core.utils.http.HttpFixtures.defaultSuccessHttpResponse
import static io.mzlnk.oauth2.exchange.core.utils.http.HttpFixtures.jsonResponseBody
import static io.mzlnk.oauth2.exchange.core.utils.http.rule.HttpRules.get
import static io.mzlnk.oauth2.exchange.core.utils.http.rule.HttpRules.withQueryParameter

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

        def exchangeClient = new FacebookAuthorizationCodeExchangeClient(
                'some-client-id',
                'some-client-secret',
                'some-redirect-uri'
        )

        def responseHandler = new FacebookAuthorizationCodeExchangeResponseHandler(new ObjectMapper())

        this.exchange = new FacebookAuthorizationCodeExchange.Builder()
                .httpClient(httpClient)
                .exchangeClient(exchangeClient)
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
    void "Should return exception when exchange failed"() {
    }

    @Test
    void "Should return exception when exchange does not return 2xx or 4xx response"() {
    }

    @Test
    void "Should return exception when provide empty authorization code"() {
        // TODO: GH-30
    }

    @Test
    void "Should return exception when provide null authorization code"() {
        // TODO: GH-30
    }

    @Test
    void "Should not create exchange when provide null exchange client"() {
        // TODO: GH-30
    }

}
