package io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents a response returned during authorization code for token exchange in OAuth2 authorization code flow
 * for Facebook identity provider.
 * <br />
 * The example response returned in JSON response body format:
 * <pre>
 * {
 *   "access_token": "some-access-token",
 *   "expires_in": 5000,
 *   "token_type": "Bearer"
 * }
 * </pre>
 * The structure of the response and all its details are created and documented based on the information posted on the official
 * <a href="https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow/">documentation site</a>.
 */
public final class FacebookOAuth2TokenResponse extends AbstractOAuth2TokenResponse {

    private FacebookOAuth2TokenResponse(@NotNull Map<String, Object> values) {
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
     * Returns the additional token, which can be used to obtain new access tokens using the same authorization grant
     *
     * <i>Note:</i> This method is not supported for Facebook OAuth2 authorization code flow because there is no data
     * related to refresh token returned in token response from authorization server.
     *
     * @return none
     * @throws UnsupportedOperationException
     */
    @Override
    public String getRefreshToken() {
        throw new UnsupportedOperationException("This method is not supported by Facebook OAuth2 authorization code flow");
    }

    /**
     * Returns the scope of the access token.
     *
     * <i>Note:</i> This method is not supported for Facebook OAuth2 authorization code flow because there is no data
     * related to scope returned in token response from authorization server.
     *
     * @return none
     * @throws UnsupportedOperationException
     */
    @Override
    public String getScope() {
        throw new UnsupportedOperationException("This method is not supported by Facebook OAuth2 authorization code flow");
    }

    /**
     * Represents factory to create OAuth2 token response received from Facebook authorization provider.
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
            return new FacebookOAuth2TokenResponse(values);
        }

    }

}
