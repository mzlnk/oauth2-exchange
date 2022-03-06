package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.MicrosoftAuthorizationCodeExchangeResponse;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

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
public final class MicrosoftAuthorizationCodeExchangeResponseHandler extends AbstractJsonBodyAuthorizationCodeExchangeResponseHandler<MicrosoftAuthorizationCodeExchangeResponse> {

    /**
     * Constructs a handler with a given Jackson object mapper used during JSON body deserialization.
     *
     * @param objectMapper Jackson object mapper used to parse incoming JSON body
     */
    public MicrosoftAuthorizationCodeExchangeResponseHandler(@NotNull ObjectMapper objectMapper) {
        super(objectMapper);
    }

    /**
     * Construct an instance of a {@link MicrosoftAuthorizationCodeExchangeResponse} response from given map of values.
     *
     * @param values non-null map of key-value pairs related to the exchange response
     */
    @Override
    protected MicrosoftAuthorizationCodeExchangeResponse convertValues(@NotNull Map<String, Object> values) {
        return MicrosoftAuthorizationCodeExchangeResponse.from(values);
    }

    /**
     * Handles error HTTP response by throwing an {@link ExchangeException} which consists of an error message
     * retrieved from the HTTP response.
     *
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
    protected MicrosoftAuthorizationCodeExchangeResponse handleErrorResponse(@NotNull Response response) {
        return response.code() == 400
                ? this.handleBadRequestResponse(response)
                : super.handleErrorResponse(response);
    }

    private MicrosoftAuthorizationCodeExchangeResponse handleBadRequestResponse(Response response) {
        var jsonResponse = this.readJsonBody(response);

        var message = "Exchange failed. Cause: Bad Request - %s".formatted(jsonResponse.get("error_description"));
        throw new ExchangeException(message, response);
    }

}
