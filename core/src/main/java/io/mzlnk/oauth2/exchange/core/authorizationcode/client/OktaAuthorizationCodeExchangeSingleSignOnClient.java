package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an implementation of {@link AuthorizationCodeExchangeClient} for Okta OAuth2 authorization code flow.
 * This type of the client is dedicated for use case where your users are all part of your Okta organization and
 * you would just like to offer them single sign-on
 * <br />
 * Implementation of the client is created based on information posted on official
 * <a href="https://developer.okta.com/docs/reference/api/oidc/">documentation site</a>.
 */
public final class OktaAuthorizationCodeExchangeSingleSignOnClient extends OktaAuthorizationCodeExchangeClient {

    private final String oktaDomain;

    /**
     * Construct an instance with given client ID, client secret (associated with the client ID), redirection URI and Okta domain.
     *
     * @param clientId     non-null string representation of the client ID
     * @param clientSecret non-null string representation of the client secret
     * @param redirectUri  non-null string representation of the redirection URI
     * @param oktaDomain   non-null string representation of the Okta domain
     * @throws NullPointerException if any of the parameters is null
     */
    public OktaAuthorizationCodeExchangeSingleSignOnClient(@NotNull String clientId,
                                                           @NotNull String clientSecret,
                                                           @NotNull String redirectUri,
                                                           @NotNull String oktaDomain) {
        super(clientId, clientSecret, redirectUri);

        Preconditions.checkNotNull(oktaDomain, "Parameter `oktaDomain` cannot be null.");
        this.oktaDomain = oktaDomain;
    }

    /**
     * Returns the client base URL which the request for exchange authorization code for a token is sent to. For this type of
     * the Okta OAuth2 authorization code flow related client, the client base URL is <i>{oktaDomain}/oauth2</i>, where:
     * <ul>
     *     <li><b>oktaDomain</b> - string representation of the Okta domain</li>
     * </ul>
     *
     * @return non-null string representation of the client base URL
     */
    @NotNull
    @Override
    public String getClientBaseUrl() {
        return "%s/oauth2".formatted(this.oktaDomain);
    }

    /**
     * Returns the Okta domain which the client is pointing out to.
     *
     * @return non-null string representation of the Okta domain
     */
    public String getOktaDomain() {
        return this.oktaDomain;
    }

}
