package io.mzlnk.oauth2.exchange.core.authorizationcode

import io.mzlnk.oauth2.exchange.core.authorizationcode.client.GitHubAuthorizationCodeExchangeClient
import io.mzlnk.oauth2.exchange.core.utils.http.HttpResponse
import io.mzlnk.oauth2.exchange.core.utils.http.MockHttpClientInterceptor
import okhttp3.OkHttpClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static io.mzlnk.oauth2.exchange.core.utils.TestUtils.defaultSuccessHttpResponse
import static io.mzlnk.oauth2.exchange.core.utils.TestUtils.loadResourceAsString
import static org.mockito.Mockito.when

class GitHubAuthorizationCodeExchangeTest {

    private static String BASE_PATH = 'io/mzlnk/oauth2/exchange/core/authorizationcode/GitHubAuthorizationCodeExchangeTest'

    private GitHubAuthorizationCodeExchange exchange
    private HttpResponse response

    @BeforeEach
    void "Set up tests"() {
        this.response = defaultSuccessHttpResponse()

        def httpClient = new OkHttpClient.Builder()
                .addInterceptor(new MockHttpClientInterceptor(response: this.response))
                .build()

        def exchangeClient = new GitHubAuthorizationCodeExchangeClient(
                'some-client-id',
                'some-client-secret',
                'some-redirect-uri'
        )

        this.exchange = new GitHubAuthorizationCodeExchange.Builder()
                .httpClient(httpClient)
                .exchangeClient(exchangeClient)
                .build()
    }

    @Test
    void "Should return token response"() {
        given:
        def json = loadResourceAsString("${BASE_PATH}/success-response.json")
        when(this.response.getResponseBody()).thenReturn(json)

        when:
        def response = this.exchange.exchangeAuthorizationCode('some-code')

        then:
        assert response != null
        assert response.getAccessToken() == 'some-access-token'
        assert response.getScope() == 'some-scope'
        assert response.getTokenType() == 'bearer'
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
