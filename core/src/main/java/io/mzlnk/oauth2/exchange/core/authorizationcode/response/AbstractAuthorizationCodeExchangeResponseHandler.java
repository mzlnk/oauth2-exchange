package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents a generic template for handler of the incoming HTTP response during authorization code exchange- either successful or error one
 * and return the object consisting of the response data.
 *
 * @param <R> response type which extends {@link Map} with {@link String}-{@link Object} key-value pair
 */
public abstract class AbstractAuthorizationCodeExchangeResponseHandler<R extends Map<String, Object>> implements AuthorizationCodeExchangeResponseHandler<R> {

    /**
     * Handles successful HTTP response and constructs a {@link Map} like response object which consists of data retrieved from incoming HTTP response.
     *
     * @param response incoming successful HTTP response
     * @return response which consists of response data in {@link Map} form
     */
    protected abstract R handleSuccessfulResponse(@NotNull Response response);

    /**
     * Handles error HTTP response and constructs a {@link Map} like response object which consists of data retrieved from incoming HTTP response.
     *
     * @param response incoming error HTTP response
     * @return response which consists of response data in {@link Map} form
     */
    protected abstract R handleErrorResponse(@NotNull Response response);

    /**
     * Handles HTTP response and constructs a {@link Map} like response object which consists of data retrieved from incoming HTTP response.
     *
     * <p>This method returns result of:</p>
     * <ul>
     *     <li>{@link #handleSuccessfulResponse(Response)} when incoming HTTP response is a successful one</li>
     *     <li>{@link #handleErrorResponse(Response)} result when incoming HTTP response is an error one</li>
     * </ul>
     *
     * <p>The fact whether the incoming HTTP response is successful or not is determined by {@link Response#isSuccessful()} method.</p>
     *
     * @param response incoming HTTP response
     * @return response which consists of response data in {@link Map} form
     */
    @Override
    public R handleResponse(@NotNull Response response) {
        return response.isSuccessful() ? handleSuccessfulResponse(response) : handleErrorResponse(response);
    }

}
