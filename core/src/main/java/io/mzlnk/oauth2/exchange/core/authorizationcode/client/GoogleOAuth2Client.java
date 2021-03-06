package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an implementation of {@link OAuth2Client} for Google OAuth2 authorization code flow.
 * Implementation of the client is created based on information posted on official
 * <a href="https://developers.google.com/identity/protocols/oauth2/web-server">documentation site</a>.
 */
public final class GoogleOAuth2Client extends AbstractOAuth2Client {

    /**
     * Construct an instance with given client ID, client secret (associated with the client ID) and redirection URI.
     *
     * @param clientId     non-null string representation of the client ID
     * @param clientSecret non-null string representation of the client secret
     * @param redirectUri  non-null string representation of the redirection URI
     * @throws NullPointerException if any of the parameters is null
     */
    public GoogleOAuth2Client(@NotNull String clientId,
                              @NotNull String clientSecret,
                              @NotNull String redirectUri) {
        super(clientId, clientSecret, redirectUri);
    }

    /**
     * Returns the token URL which the request for exchange authorization code for a token is sent to. For GitHub
     * OAuth2 authorization code flow, the token URL is <i>https://oauth2.googleapis.com/token"</i>.
     *
     * @return non-null string representation of the token URL
     */
    @NotNull
    @Override
    public String getTokenUrl() {
        return "https://oauth2.googleapis.com/token";
    }

}
