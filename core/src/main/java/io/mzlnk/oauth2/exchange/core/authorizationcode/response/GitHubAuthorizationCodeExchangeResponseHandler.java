package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.GitHubAuthorizationCodeExchangeResponse;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents a handler of the incoming HTTP response during authorization code exchange for GitHub auth provider.
 * <p>
 * The handler supports both successful and error incoming HTTP responses - based on the information posted on the official
 * <a href="https://docs.github.com/en/developers/apps/managing-oauth-apps/troubleshooting-oauth-app-access-token-request-errors">documentation site</a>
 * </p>
 * <br />
 * The example successful response returned in JSON response body format:
 * <pre>
 * {
 *   "access_token": "some-access-token",
 *   "scope": "some-scope",
 *   "token_type": "bearer"
 * }
 * </pre>
 * The example HTTP 4xx error response returned in JSON response body format:
 * <pre>
 * {
 *   "error": "some-error",
 *   "error_description": "some-error-description",
 *   "error_uri": "some-error-uri"
 * }
 * </pre>
 */
public final class GitHubAuthorizationCodeExchangeResponseHandler extends AbstractJsonBodyAuthorizationCodeExchangeResponseHandler<GitHubAuthorizationCodeExchangeResponse> {

    /**
     * Constructs a handler with a given Jackson object mapper used during JSON body deserialization.
     *
     * @param objectMapper Jackson object mapper used to parse incoming JSON body
     */
    public GitHubAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    /**
     * Handles HTTP response and constructs a {@link Map} like response object which consists of data retrieved from incoming HTTP response.
     *
     * <p>This method returns result of:</p>
     * <ul>
     *     <li>{@link #handleSuccessfulResponse(Response)} when incoming HTTP response is a successful one</li>
     *     <li>{@link #handleErrorResponse(Response)} when incoming HTTP response is an error one (different than HTTP 400 Bad Request)</li>
     *     <li>{@link #handleBadRequestResponse(Response)} when incoming HTTP response is an error HTTP 400 Bad Request one</li>
     * </ul>
     *
     * <p>
     * The fact whether the incoming HTTP response is successful or not is determined by {@link Response#isSuccessful()} method
     * and existence of <code>error</code> field in JSON response body (only under some circumstances - for an explanation, see <i>Notes</i>
     * section below).
     * </p>
     *
     * <p>
     * <i>Note:</i>
     * <br />
     * According to the official <a href="https://docs.github.com/en/developers/apps/managing-oauth-apps/troubleshooting-oauth-app-access-token-request-errors">documentation site</a>
     * GitHub returns HTTP 400 Bad Request related responses with HTTP 200 OK status.
     * </p>
     *
     * If the HTTP response is an error one - the method throws an {@link ExchangeException} which consists of an error message
     * retrieved from the HTTP response.
     *
     * The exception message is retrieved from:
     * <ul>
     *     <li><code>error_description</code> field from JSON error response body if response is the HTTP 400 one</li>
     *     <li>HTTP status message if response is different than the HTTP 400 one</li>
     * </ul>
     *
     * @param response incoming HTTP response
     * @return response which consists of response data in {@link Map} form if HTTP response is the successful one
     * @throws ExchangeException exception which consists of an error message if HTTP response is the error one
     */
    @Override
    public GitHubAuthorizationCodeExchangeResponse handleResponse(@NotNull Response response) {
        /*
         * This bypass is essential to handle error response properly as GitHub returns
         * HTTP 400 Bad Request related responses with HTTP 200 OK status.
         */

        if (!response.isSuccessful()) {
            return super.handleErrorResponse(response);
        }

        return this.isBadRequestResponse(response)
                ? this.handleBadRequestResponse(response)
                : super.handleSuccessfulResponse(response);
    }

    /**
     * Construct an instance of a {@link GitHubAuthorizationCodeExchangeResponse} response from given map of values.
     *
     * @param values non-null map of key-value pairs related to the exchange response
     */
    @Override
    protected GitHubAuthorizationCodeExchangeResponse convertValues(@NotNull Map<String, Object> values) {
        return GitHubAuthorizationCodeExchangeResponse.from(values);
    }

    private GitHubAuthorizationCodeExchangeResponse handleBadRequestResponse(Response response) {
        var jsonResponse = this.readJsonBody(response);

        var message = "Exchange failed. Cause: Bad Request - %s".formatted(jsonResponse.getOrDefault("error_description", ""));
        throw new ExchangeException(message, response);
    }

    private boolean isBadRequestResponse(Response response) {
        var jsonResponse = this.readJsonBody(response);
        return jsonResponse.containsKey("error");
    }

}
