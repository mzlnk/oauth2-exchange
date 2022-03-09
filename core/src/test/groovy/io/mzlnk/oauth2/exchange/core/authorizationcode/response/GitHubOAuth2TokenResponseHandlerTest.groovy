package io.mzlnk.oauth2.exchange.core.authorizationcode.response

import com.fasterxml.jackson.databind.ObjectMapper
import io.mzlnk.oauth2.exchange.core.ExchangeException
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.GitHubOAuth2TokenResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static io.mzlnk.oauth2.exchange.core.utils.TestUtils.loadResourceAsString
import static io.mzlnk.oauth2.exchange.core.utils.http.HttpFixtures.*
import static org.junit.jupiter.api.Assertions.assertThrows

class GitHubOAuth2TokenResponseHandlerTest {

    private static String BASE_PATH = 'io/mzlnk/oauth2/exchange/core/authorizationcode/response/GitHubOAuth2TokenResponseHandlerTest'

    private GitHubOAuth2TokenResponseHandler responseHandler

    @BeforeEach
    void "Set up tests"() {
        this.responseHandler = new GitHubOAuth2TokenResponseHandler(
                new GitHubOAuth2TokenResponse.Factory(),
                new ObjectMapper()
        )
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
        assert response.getScope() == 'some-scope'
        assert response.getTokenType() == 'bearer'
    }

    @Test
    void "Should handle bad request response"() {
        given:
        def json = loadResourceAsString("${BASE_PATH}/error-response-invalid-client.json")

        // GitHub returns HTTP 400 Bad Request related responses with HTTP 200 OK status.
        def httpResponse = defaultSuccessHttpResponse() {
            body(jsonResponseBody(json))
        }

        when:
        def exception = assertThrows(
                ExchangeException,
                () -> this.responseHandler.handleResponse(httpResponse)
        )

        then:
        assert exception != null
        assert exception.message == 'Exchange failed. Cause: Bad Request - The client_id and/or client_secret passed are incorrect.'
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
        def responseFactory = new GitHubOAuth2TokenResponse.Factory()
        def objectMapper = null

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new GitHubOAuth2TokenResponseHandler(responseFactory, objectMapper)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `objectMapper` cannot be null.'
    }

    @Test
    void "Should return exception if responseFactory parameter is null"() {
        given:
        def responseFactory = null
        def objectMapper = new ObjectMapper()

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new GitHubOAuth2TokenResponseHandler(responseFactory, objectMapper)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `responseFactory` cannot be null.'
    }

}
