package io.mzlnk.oauth2.exchange.core.authorizationcode.response

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static io.mzlnk.oauth2.exchange.core.utils.TestUtils.loadResourceAsString
import static io.mzlnk.oauth2.exchange.core.utils.http.HttpFixtures.defaultSuccessHttpResponse
import static io.mzlnk.oauth2.exchange.core.utils.http.HttpFixtures.jsonResponseBody

class KeycloakAuthorizationCodeExchangeResponseHandlerTest {

    private static String BASE_PATH = 'io/mzlnk/oauth2/exchange/core/authorizationcode/response/KeycloakAuthorizationCodeExchangeResponseHandlerTest'

    private KeycloakAuthorizationCodeExchangeResponseHandler responseHandler

    @BeforeEach
    void "Set up tests"() {
        this.responseHandler = new KeycloakAuthorizationCodeExchangeResponseHandler(new ObjectMapper())
    }

    @Test
    void "Should handle successful response"() {
        given:
        def json = loadResourceAsString("${BASE_PATH}/success-response.json")
        def httpResponse = defaultSuccessHttpResponse() {
            body(jsonResponseBody(json))
        }

        when:
        def response = this.responseHandler.handleSuccessfulResponse(httpResponse)

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

}
