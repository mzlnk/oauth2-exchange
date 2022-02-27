package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a template for all types of clients available for OAuth2 authorization code flow for Microsoft identity provider.<br />
 * Currently, there are available 4 different clients:
 * <ul>
 *     <li>{@link MicrosoftAuthorizationCodeExchangeCommonClient}</li>
 *     <li>{@link MicrosoftAuthorizationCodeExchangeConsumerClient}</li>
 *     <li>{@link MicrosoftAuthorizationCodeExchangeOrganizationsClient}</li>
 *     <li>{@link MicrosoftAuthorizationCodeExchangeAzureADClient}</li>
 * </ul>
 * All implementations are created based on information found posted on official
 * <a href="https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-auth-code-flow">documentation site</a>.
 */
public sealed abstract class MicrosoftAuthorizationCodeExchangeClient
        extends AbstractAuthorizationCodeExchangeClient
        permits MicrosoftAuthorizationCodeExchangeCommonClient, MicrosoftAuthorizationCodeExchangeConsumerClient, MicrosoftAuthorizationCodeExchangeOrganizationsClient, MicrosoftAuthorizationCodeExchangeAzureADClient {

    /**
     * Construct an instance with given client ID, client secret (associated with the client ID) and redirection URI.
     *
     * @param clientId     non-null string representation of the client ID
     * @param clientSecret non-null string representation of the client secret
     * @param redirectUri  non-null string representation of the redirection URI
     * @throws NullPointerException if any of the parameters is null
     */
    protected MicrosoftAuthorizationCodeExchangeClient(@NotNull String clientId,
                                                       @NotNull String clientSecret,
                                                       @NotNull String redirectUri) {
        super(clientId, clientSecret, redirectUri);
    }

}
