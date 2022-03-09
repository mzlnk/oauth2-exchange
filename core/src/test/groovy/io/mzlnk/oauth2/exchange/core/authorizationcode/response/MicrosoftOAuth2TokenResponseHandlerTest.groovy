package io.mzlnk.oauth2.exchange.core.authorizationcode.response

import com.fasterxml.jackson.databind.ObjectMapper
import io.mzlnk.oauth2.exchange.core.ExchangeException
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.MicrosoftOAuth2TokenResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static io.mzlnk.oauth2.exchange.core.utils.TestUtils.loadResourceAsString
import static io.mzlnk.oauth2.exchange.core.utils.http.HttpFixtures.*
import static org.junit.jupiter.api.Assertions.assertThrows

class MicrosoftOAuth2TokenResponseHandlerTest {

    private static String BASE_PATH = 'io/mzlnk/oauth2/exchange/core/authorizationcode/response/MicrosoftOAuth2TokenResponseHandlerTest'

    private MicrosoftOAuth2TokenResponseHandler responseHandler

    @BeforeEach
    void "Set up tests"() {
        this.responseHandler = new MicrosoftOAuth2TokenResponseHandler(
                new MicrosoftOAuth2TokenResponse.Factory(),
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
        def response = this.responseHandler.handleResponse(httpResponse) as MicrosoftOAuth2TokenResponse

        then:
        assert response != null
        assert response.getAccessToken() == 'some-access-token'
        assert response.getTokenType() == 'Bearer'
        assert response.getExpiresIn() == 5000
        assert response.getScope() == 'some-scope'
        assert response.getRefreshToken() == 'some-refresh-token'
        assert response.getIdToken() == 'some-id-token'
    }

    @Test
    void "Should handle bad request response"() {
        given:
        def json = loadResourceAsString("${BASE_PATH}/error-response-invalid-scope.json")
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
        assert exception.message == "Exchange failed. Cause: Bad Request - AADSTS70011: The provided value for the input parameter 'scope' is not valid. The scope [scope] is not valid.\r\nTrace ID: [trace-id]>\r\nCorrelation ID: [correlation-id]\r\nTimestamp: [timestamp]"
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
        def responseFactory = new MicrosoftOAuth2TokenResponse.Factory()
        def objectMapper = null

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new MicrosoftOAuth2TokenResponseHandler(responseFactory, objectMapper)
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
                () -> new MicrosoftOAuth2TokenResponseHandler(responseFactory, objectMapper)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `responseFactory` cannot be null.'
    }

}
