package io.mzlnk.oauth2.exchange.core.authorizationcode.response

import com.fasterxml.jackson.databind.ObjectMapper
import io.mzlnk.oauth2.exchange.core.ExchangeException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static io.mzlnk.oauth2.exchange.core.utils.TestUtils.loadResourceAsString
import static io.mzlnk.oauth2.exchange.core.utils.http.HttpFixtures.*
import static org.junit.jupiter.api.Assertions.assertThrows

class OktaAuthorizationCodeExchangeResponseHandlerTest {

    private static String BASE_PATH = 'io/mzlnk/oauth2/exchange/core/authorizationcode/response/OktaAuthorizationCodeExchangeResponseHandlerTest'

    private OktaAuthorizationCodeExchangeResponseHandler responseHandler

    @BeforeEach
    void "Set up tests"() {
        this.responseHandler = new OktaAuthorizationCodeExchangeResponseHandler(new ObjectMapper())
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
        assert response.getTokenType() == 'Bearer'
        assert response.getExpiresIn() == 3600
        assert response.getScope() == 'some-scope'
        assert response.getRefreshToken() == 'some-refresh-token'
        assert response.getIdToken() == 'some-id-token'
    }

    @Test
    void "Should handle bad request response"() {
        given:
        def json = loadResourceAsString("${BASE_PATH}/error-response-missing-redirect-uri.json")
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
        assert exception.message == 'Exchange failed. Cause: Bad Request - Missing redirect uri.'
        assert exception.response == httpResponse
    }

    @Test
    void "Should handle unauthorized response"() {
        given:
        def json = loadResourceAsString("${BASE_PATH}/error-response-invalid-client.json")
        def httpResponse = defaultUnauthorizedHttpResponse() {
            body(jsonResponseBody(json))
        }

        when:
        def exception = assertThrows(
                ExchangeException,
                () -> this.responseHandler.handleResponse(httpResponse)
        )

        then:
        assert exception != null
        assert exception.message == 'Exchange failed. Cause: Unauthorized - No client credentials found.'
        assert exception.response == httpResponse
    }

    @Test
    void "Should handle error response other than bad request or unauthorized one"() {
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
                () -> new OktaAuthorizationCodeExchangeResponseHandler(objectMapper)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `objectMapper` cannot be null.'
    }

}
