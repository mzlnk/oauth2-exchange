package io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents a successful response from authorization server to a token exchange.
 * The response structure is in form of key-value pairs and follows criteria specified in
 * <a href="https://datatracker.ietf.org/doc/html/rfc6749#section-5">Section 5 of RFC6749 document</a>.
 */
public interface OAuth2TokenResponse extends Map<String, Object> {

    /**
     * Returns the access token issued by the authorization server.
     *
     * @return string representation of the access token
     */
    String getAccessToken();

    /**
     * Returns the type of the token issued. More details about this field can be found in
     * <a href="https://datatracker.ietf.org/doc/html/rfc6749#section-7.1">Section 7.1 of RFC6749 document</a>.
     *
     * @return string representation of the token type
     */
    String getTokenType();

    /**
     * Returns the lifetime in seconds of the access token.
     *
     * @return number representation of an token expiration time
     */
    Integer getExpiresIn();

    /**
     * Returns the additional token, which can be used to obtain new access tokens using the same authorization grant
     *
     * @return string representation of a refresh token
     */
    String getRefreshToken();

    /**
     * Returns the scope of the access token.
     *
     * @return string representation of the scope
     */
    String getScope();

    /**
     * Represents a factory responsible for creating {@link OAuth2TokenResponse} response from given data
     */
    interface Factory {

        /**
         * Constructs {@link OAuth2TokenResponse} response from given map containing token response data
         * received from given authorization server.
         *
         * @param values non-null map of values (usually representation of a JSON HTTP response body)
         * @return instance of a response based on given values
         */
        OAuth2TokenResponse create(@NotNull Map<String, Object> values);

    }


}
