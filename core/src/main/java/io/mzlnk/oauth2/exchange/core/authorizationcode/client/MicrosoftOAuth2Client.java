package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a template for all types of clients available for OAuth2 authorization code flow for Microsoft identity provider.<br />
 * Currently, there are available 4 different clients:
 * <ul>
 *     <li>{@link MicrosoftOAuth2CommonClient}</li>
 *     <li>{@link MicrosoftOAuth2ConsumerClient}</li>
 *     <li>{@link MicrosoftOAuth2OrganizationsClient}</li>
 *     <li>{@link MicrosoftOAuth2AzureADClient}</li>
 * </ul>
 * All implementations are created based on information found posted on official
 * <a href="https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-auth-code-flow">documentation site</a>.
 */
public sealed abstract class MicrosoftOAuth2Client
        extends AbstractOAuth2Client
        permits MicrosoftOAuth2CommonClient, MicrosoftOAuth2ConsumerClient, MicrosoftOAuth2OrganizationsClient, MicrosoftOAuth2AzureADClient {

    /**
     * Construct an instance with given client ID, client secret (associated with the client ID) and redirection URI.
     *
     * @param clientId     non-null string representation of the client ID
     * @param clientSecret non-null string representation of the client secret
     * @param redirectUri  non-null string representation of the redirection URI
     * @throws NullPointerException if any of the parameters is null
     */
    protected MicrosoftOAuth2Client(@NotNull String clientId,
                                    @NotNull String clientSecret,
                                    @NotNull String redirectUri) {
        super(clientId, clientSecret, redirectUri);
    }

}
