package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an implementation of {@link OAuth2Client} for Microsoft OAuth2 authorization code flow.
 * This type of the client is dedicated for allowing only users with personal Microsoft accounts (MSA) to sign into the application.
 * <br />
 * Implementation of the client is created based on information posted on official
 * <a href="https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-auth-code-flow">documentation site</a>.
 */
public final class MicrosoftOAuth2ConsumerClient extends MicrosoftOAuth2Client {

    /**
     * Construct an instance with given client ID, client secret (associated with the client ID) and redirection URI.
     *
     * @param clientId     non-null string representation of the client ID
     * @param clientSecret non-null string representation of the client secret
     * @param redirectUri  non-null string representation of the redirection URI
     * @throws NullPointerException if any of the parameters is null
     */
    public MicrosoftOAuth2ConsumerClient(@NotNull String clientId,
                                         @NotNull String clientSecret,
                                         @NotNull String redirectUri) {
        super(clientId, clientSecret, redirectUri);
    }

    /**
     * Returns the token URL which the request for exchange authorization code for a token is sent to. For this type of
     * the Microsoft OAuth2 authorization code flow related client, the client base URL is <i>https://login.microsoftonline.com/consumers</i>.
     *
     * @return non-null string representation of the token URL
     */
    @NotNull
    @Override
    public String getTokenUrl() {
        return "https://login.microsoftonline.com/consumers/oauth2/v2.0/token";
    }

}
