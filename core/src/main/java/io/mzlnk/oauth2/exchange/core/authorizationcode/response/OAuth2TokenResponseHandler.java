package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.OAuth2TokenResponse;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a handler of the incoming HTTP response during authorization code exchange- either successful or error one
 * and return the object consisting of the response data.
 */
public interface OAuth2TokenResponseHandler {

    /**
     * Handles HTTP response and constructs an {@link OAuth2TokenResponse} response object which consists of data retrieved from incoming HTTP response
     *
     * @param response incoming HTTP response
     * @return {@link OAuth2TokenResponse} response which consists of response data
     */
    OAuth2TokenResponse handleResponse(@NotNull Response response);

}
