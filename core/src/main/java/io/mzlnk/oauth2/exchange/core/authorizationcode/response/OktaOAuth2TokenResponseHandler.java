package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.OAuth2TokenResponse;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.OktaOAuth2TokenResponse;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a handler of the incoming HTTP response during authorization code exchange for Keycloak auth provider.
 * <p>
 * The handler supports both successful and error incoming HTTP responses - based on the information posted on the official
 * <a href="https://www.keycloak.org/docs/latest/authorization_services/">documentation site</a>
 * </p>
 * <br />
 * The example successful response returned in JSON response body format:
 * <pre>
 * {
 *   "access_token": "some-access-token",
 *   "token_type": "Bearer",
 *   "expires_in": 3600,
 *   "scope": "some-scope",
 *   "refresh_token": "some-refresh-token",
 *   "id_token": "some-id-token"
 * }
 * </pre>
 * The example HTTP 4xx error response returned in JSON response body format:
 * <pre>
 * {
 *   "error" : "some-error",
 *   "error_description" : "some-error-description"
 * }
 * </pre>
 */
public final class OktaOAuth2TokenResponseHandler extends AbstractJsonBodyOAuth2TokenResponseHandler {

    /**
     * Constructs a handler with a given Jackson object mapper used during JSON body deserialization.
     *
     * @param objectMapper Jackson object mapper used to parse incoming JSON body
     */
    public OktaOAuth2TokenResponseHandler(@NotNull ObjectMapper objectMapper) {
        super(new OktaOAuth2TokenResponse.Factory(), objectMapper);
    }

    /**
     * Handles error HTTP response by throwing an {@link ExchangeException} which consists of an error message
     * retrieved from the HTTP response.
     * <p>
     * The exception message is retrieved from:
     * <ul>
     *     <li><code>error_description</code> field from JSON error response body if response is the HTTP 400/401 one</li>
     *     <li>HTTP status message if response is different than the HTTP 400/401 one</li>
     * </ul>
     *
     * @param response incoming error HTTP response
     * @return none
     * @throws ExchangeException exception which consists of an error message
     */
    @Override
    protected OAuth2TokenResponse handleErrorResponse(@NotNull Response response) {
        return switch (response.code()) {
            case 400 -> this.handleBadRequestResponse(response);
            case 401 -> this.handleUnauthorizedResponse(response);
            default -> super.handleErrorResponse(response);
        };
    }

    private OAuth2TokenResponse handleBadRequestResponse(Response response) {
        var jsonResponse = this.readJsonBody(response);

        var message = "Exchange failed. Cause: Bad Request - %s".formatted(jsonResponse.get("error_description"));
        throw new ExchangeException(message, response);
    }

    private OAuth2TokenResponse handleUnauthorizedResponse(Response response) {
        var jsonResponse = this.readJsonBody(response);

        var message = "Exchange failed. Cause: Unauthorized - %s".formatted(jsonResponse.get("error_description"));
        throw new ExchangeException(message, response);
    }

}
