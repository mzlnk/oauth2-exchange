package io.mzlnk.oauth2.exchange.core.authorizationcode.response

import com.fasterxml.jackson.databind.ObjectMapper
import io.mzlnk.oauth2.exchange.core.ExchangeException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static io.mzlnk.oauth2.exchange.core.utils.TestUtils.loadResourceAsString
import static io.mzlnk.oauth2.exchange.core.utils.http.HttpFixtures.*
import static org.junit.jupiter.api.Assertions.assertThrows

class GoogleOAuth2TokenResponseHandlerTest {

    private static String BASE_PATH = 'io/mzlnk/oauth2/exchange/core/authorizationcode/response/GoogleOAuth2TokenResponseHandlerTest'

    private GoogleOAuth2TokenResponseHandler responseHandler

    @BeforeEach
    void "Set up tests"() {
        this.responseHandler = new GoogleOAuth2TokenResponseHandler(new ObjectMapper())
    }

    @Test
    void "Should handle successful response"() {
        given:
        def json = loadResourceAsString("${BASE_PATH}/success-response.json")
        def httpResponse = defaultSuccessHttpResponse() {
            body(jsonResponseBody(json))
        }

        when:
        def response = this.responseHandler.handleResponse(httpResponse)

        then:
        assert response != null
        assert response.getAccessToken() == 'some-access-token'
        assert response.getExpiresIn() == 5000
        assert response.getRefreshToken() == 'some-refresh-token'
        assert response.getScope() == 'some-scope'
        assert response.getTokenType() == 'Bearer'
    }

    @Test
    void "Should handle bad request response"() {
        given:
        def json = loadResourceAsString("${BASE_PATH}/error-response-invalid-client.json")
        def httpResponse = defaultBadRequestHttpResponse() {
            body(jsonResponseBody(json))
        }

        when:
        def exception = assertThrows(
                ExchangeException,
                () -> this.responseHandler.handleResponse(httpResponse)
        )

        then:
        assert exception != null
        assert exception.message == 'Exchange failed. Cause: Bad Request - Unauthorized'
        assert exception.response == httpResponse
    }

    @Test
    void "Should handle error response other than bad request one"() {
        given:
        def httpResponse = defaultInternalServerErrorHttpResponse()

        when:
        def exception = assertThrows(
                ExchangeException,
                () -> this.responseHandler.handleResponse(httpResponse)
        )

        then:
        assert exception != null
        assert exception.message == 'Exchange failed. Cause: Internal Server Error'
        assert exception.response == httpResponse
    }

    @Test
    void "Should return exception if objectMapper parameter is null"() {
        given:
        def objectMapper = null

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new GoogleOAuth2TokenResponseHandler(objectMapper)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `objectMapper` cannot be null.'
    }

}
