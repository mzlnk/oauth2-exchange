package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an implementation of {@link AuthorizationCodeExchangeClient} for Facebook OAuth2 authorization code flow.
 * Implementation of the client is created based on information posted on official
 * <a href="https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow/">documentation site</a>.
 */
public final class FacebookAuthorizationCodeExchangeClient extends AbstractAuthorizationCodeExchangeClient {

    /**
     * Construct an instance with given client ID, client secret (associated with the client ID) and redirection URI.
     *
     * @param clientId     non-null string representation of the client ID
     * @param clientSecret non-null string representation of the client secret
     * @param redirectUri  non-null string representation of the redirection URI
     * @throws NullPointerException if any of the parameters is null
     */
    public FacebookAuthorizationCodeExchangeClient(@NotNull String clientId,
                                                   @NotNull String clientSecret,
                                                   @NotNull String redirectUri) {
        super(clientId, clientSecret, redirectUri);
    }

    /**
     * Returns the client base URL which the request for exchange authorization code for a token is sent to. For Facebook
     * OAuth2 authorization code flow, the client base URL is <i>https://graph.facebook.com</i>.
     *
     * @return non-null string representation of the client base URL
     */
    @NotNull
    @Override
    public String getClientBaseUrl() {
        return "https://graph.facebook.com";
    }

}
