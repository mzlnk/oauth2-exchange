package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a template for all types of clients available for OAuth2 authorization code flow for Okta identity provider.<br />
 * Currently, there are available 2 different clients:
 * <ul>
 *     <li>{@link OktaAuthorizationCodeExchangeSingleSignOnClient}</li>
 *     <li>{@link OktaAuthorizationCodeExchangeAuthorizationServerClient}</li>
 * </ul>
 * All implementations are created based on information found posted on official
 * <a href="https://developer.okta.com/docs/reference/api/oidc/">documentation site</a>.
 */
public sealed abstract class OktaAuthorizationCodeExchangeClient
        extends AbstractAuthorizationCodeExchangeClient
        permits OktaAuthorizationCodeExchangeSingleSignOnClient, OktaAuthorizationCodeExchangeAuthorizationServerClient {

    /**
     * Construct an instance with given client ID, client secret (associated with the client ID) and redirection URI.
     *
     * @param clientId     non-null string representation of the client ID
     * @param clientSecret non-null string representation of the client secret
     * @param redirectUri  non-null string representation of the redirection URI
     * @throws NullPointerException if any of the parameters is null
     */
    protected OktaAuthorizationCodeExchangeClient(@NotNull String clientId,
                                                  @NotNull String clientSecret,
                                                  @NotNull String redirectUri) {
        super(clientId, clientSecret, redirectUri);
    }

}
