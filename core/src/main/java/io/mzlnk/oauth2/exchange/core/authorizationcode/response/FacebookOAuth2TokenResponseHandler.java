package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.FacebookOAuth2TokenResponse;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.OAuth2TokenResponse;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents a handler of the incoming HTTP response during authorization code exchange for Facebook auth provider.
 * <p>
 * The handler supports both successful and error incoming HTTP responses - based on the information posted on the official
 * <a href="https://developers.facebook.com/docs/facebook-login/access-tokens/debugging-and-error-handling/">documentation site</a>
 * </p>
 * <br />
 * The example successful response returned in JSON response body format:
 * <pre>
 * {
 *   "access_token": "some-access-token",
 *   "expires_in": 5000,
 *   "token_type": "Bearer"
 * }
 * </pre>
 * The example HTTP 4xx error response returned in JSON response body format:
 * <pre>
 * {
 *   "error": {
 *     "message": "some-error-message",
 *     "type": "some-type",
 *     "code": 190,
 *     "error_subcode": 400,
 *     "fbtrace_id": "some-trace-id"
 *   }
 * }
 * </pre>
 */
public final class FacebookOAuth2TokenResponseHandler extends AbstractJsonBodyOAuth2TokenResponseHandler {

    /**
     * Constructs a handler with a given Jackson object mapper used during JSON body deserialization.
     *
     * @param objectMapper Jackson object mapper used to parse incoming JSON body
     */
    public FacebookOAuth2TokenResponseHandler(@NotNull ObjectMapper objectMapper) {
        super(new FacebookOAuth2TokenResponse.Factory(), objectMapper);
    }

    /**
     * Handles error HTTP response by throwing an {@link ExchangeException} which consists of an error message
     * retrieved from the HTTP response.
     *
     * The exception message is retrieved from:
     * <ul>
     *     <li><code>error.message</code> field from JSON error response body if response is the HTTP 400 one</li>
     *     <li>HTTP status message if response is different than the HTTP 400 one</li>
     * </ul>
     *
     * @param response incoming error HTTP response
     * @return none
     * @throws ExchangeException exception which consists of an error message
     */
    @Override
    protected OAuth2TokenResponse handleErrorResponse(@NotNull Response response) {
        return response.code() == 400
                ? this.handleBadRequestResponse(response)
                : super.handleErrorResponse(response);
    }

    private OAuth2TokenResponse handleBadRequestResponse(Response response) {
        var jsonResponse = this.readJsonBody(response);

        var error = (Map<String, Object>) jsonResponse.get("error");

        var message = "Exchange failed. Cause: Bad Request - %s".formatted(error.get("message"));
        throw new ExchangeException(message, response);
    }

}
