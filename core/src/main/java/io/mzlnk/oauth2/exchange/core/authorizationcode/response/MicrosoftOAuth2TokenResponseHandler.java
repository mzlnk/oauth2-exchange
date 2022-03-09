package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.MicrosoftOAuth2TokenResponse;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.OAuth2TokenResponse;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a handler of the incoming HTTP response during authorization code exchange for Microsoft auth provider.
 * <p>
 * The handler supports both successful and error incoming HTTP responses - based on the information posted on the official
 * <a href="https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-auth-code-flow">documentation site</a>
 * </p>
 * <br />
 * The example successful response returned in JSON response body format:
 * <pre>
 * {
 *   "access_token": "some-access-token",
 *   "token_type": "Bearer",
 *   "expires_in": 5000,
 *   "scope": "some-scope",
 *   "refresh_token": "some-refresh-token",
 *   "id_token": "some-id-token"
 * }
 * </pre>
 * The example HTTP 4xx error response returned in JSON response body format:
 * <pre>
 * {
 *   "error": "some-error",
 *   "error_description": "some-error-description",
 *   "error_codes": [
 *     70011
 *   ],
 *   "timestamp": "some-timestamp",
 *   "trace_id": "some-trace-id",
 *   "correlation_id": "some-correlation-id"
 * }
 * </pre>
 */
public final class MicrosoftOAuth2TokenResponseHandler extends AbstractJsonBodyOAuth2TokenResponseHandler {

    /**
     * Constructs a handler with a given {@link OAuth2TokenResponse.Factory} response factory used to create
     * {@link OAuth2TokenResponse} response and Jackson object mapper used during JSON body deserialization.
     *
     * @param responseFactory response factory used to create {@link OAuth2TokenResponse} response
     * @param objectMapper    Jackson object mapper used to parse incoming JSON body
     */
    public MicrosoftOAuth2TokenResponseHandler(@NotNull MicrosoftOAuth2TokenResponse.Factory responseFactory,
                                               @NotNull ObjectMapper objectMapper) {
        super(responseFactory, objectMapper);
    }

    /**
     * Handles error HTTP response by throwing an {@link ExchangeException} which consists of an error message
     * retrieved from the HTTP response.
     * <p>
     * The exception message is retrieved from:
     * <ul>
     *     <li><code>error_description</code> field from JSON error response body if response is the HTTP 400 one</li>
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

        var message = "Exchange failed. Cause: Bad Request - %s".formatted(jsonResponse.get("error_description"));
        throw new ExchangeException(message, response);
    }

}
