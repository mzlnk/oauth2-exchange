package io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents a response returned during authorization code for token exchange in OAuth2 authorization code flow
 * for Google identity provider.
 * <br />
 * The example response returned in JSON response body format:
 * <pre>
 * {
 *   "access_token": "some-access-token",
 *   "expires_in": 5000,
 *   "token_type": "Bearer",
 *   "scope": "some-scope",
 *   "refresh_token": "some-refresh-token"
 * }
 * </pre>
 * The structure of the response and all its details are created and documented based on the information posted on the official
 * <a href="https://developers.google.com/identity/protocols/oauth2/web-server">documentation site</a>.
 */
public final class GoogleOAuth2TokenResponse extends AbstractOAuth2TokenResponse {

    private GoogleOAuth2TokenResponse(@NotNull Map<String, Object> values) {
        super(values);
    }

    /**
     * Returns an access token which has been returned in a HTTP response under <code>access_token</code> parameter
     *
     * @return string representation of an access token
     */
    @Override
    public String getAccessToken() {
        return (String) this.get("access_token");
    }

    /**
     * Returns a token type which has been returned in a HTTP response under <code>token_type</code> parameter
     *
     * @return string representation of a token type
     */
    @Override
    public String getTokenType() {
        return (String) this.get("token_type");
    }

    /**
     * Returns a token expiration time which has been returned in a HTTP response under <code>expires_in</code> parameter
     *
     * @return number representation of an token expiration time
     */
    @Override
    public Integer getExpiresIn() {
        return (Integer) this.get("expires_in");
    }

    /**
     * Returns a refresh token which has been returned in a HTTP response under <code>refresh_token</code> parameter
     *
     * @return string representation of a refresh token
     */
    @Override
    public String getRefreshToken() {
        return (String) this.get("refresh_token");
    }

    /**
     * Returns a scope which has been returned in a HTTP response under <code>scope</code> parameter
     *
     * @return string representation of a scope
     */
    @Override
    public String getScope() {
        return (String) this.get("scope");
    }

    /**
     * Represents factory to create OAuth2 token response received from Google authorization provider.
     */
    public static final class Factory implements OAuth2TokenResponse.Factory {

        /**
         * Construct an response from a given map of values, usually returned as a JSON HTTP body response from HTTP request
         * issued for exchanging authorization code for a token.
         *
         * @param values non-null map of values (representation of a JSON HTTP response body)
         * @return instance of a response based on given values
         * @throws NullPointerException if given map of values is null
         */
        @Override
        public OAuth2TokenResponse create(@NotNull Map<String, Object> values) {
            return new GoogleOAuth2TokenResponse(values);
        }

    }

}
