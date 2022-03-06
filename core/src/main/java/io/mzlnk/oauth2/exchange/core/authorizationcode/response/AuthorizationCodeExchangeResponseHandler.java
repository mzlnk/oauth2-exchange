package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents a handler of the incoming HTTP response during authorization code exchange- either successful or error one
 * and return the object consisting of the response data.
 *
 * @param <R> response type which extends {@link Map} with {@link String}-{@link Object} key-value pairs
 */
public interface AuthorizationCodeExchangeResponseHandler<R extends Map<String, Object>> {

    /**
     * Handles HTTP response and constructs a {@link Map} like response object which consists of data retrieved from incoming HTTP response
     *
     * @param response incoming HTTP response
     * @return response which consists of response data in {@link Map} form
     */
    R handleResponse(@NotNull Response response);

}
