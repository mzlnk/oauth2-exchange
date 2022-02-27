package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an implementation of {@link AuthorizationCodeExchangeClient} for Okta OAuth2 authorization code flow.
 * This type of the client is dedicated for use case where Okta is the authorization server for your resource server
 * <br />
 * Implementation of the client is created based on information posted on official
 * <a href="https://developer.okta.com/docs/reference/api/oidc/">documentation site</a>.
 */
public final class OktaAuthorizationCodeExchangeAuthorizationServerClient extends OktaAuthorizationCodeExchangeClient {

    private final String oktaDomain;
    private final String authorizationServerId;

    /**
     * Construct an instance with given client ID, client secret (associated with the client ID) redirection URI, Okta domain and Okta authorization server ID.
     *
     * @param clientId                  non-null string representation of the client ID
     * @param clientSecret              non-null string representation of the client secret
     * @param redirectUri               non-null string representation of the redirection URI
     * @param oktaDomain                non-null string representation of the Okta domain
     * @param oktaAuthorizationServerId non-null string representation of the Okta authorization server ID
     * @throws NullPointerException if any of the parameters is null
     */
    public OktaAuthorizationCodeExchangeAuthorizationServerClient(@NotNull String clientId,
                                                                  @NotNull String clientSecret,
                                                                  @NotNull String redirectUri,
                                                                  @NotNull String oktaDomain,
                                                                  @NotNull String oktaAuthorizationServerId) {
        super(clientId, clientSecret, redirectUri);

        Preconditions.checkNotNull(oktaDomain, "Parameter `oktaDomain` cannot be null.");
        Preconditions.checkNotNull(oktaAuthorizationServerId, "Parameter `oktaAuthorizationServerId` cannot be null.");

        this.oktaDomain = oktaDomain;
        this.authorizationServerId = oktaAuthorizationServerId;
    }

    /**
     * Returns the client base URL which the request for exchange authorization code for a token is sent to. For this type of
     * the Okta OAuth2 authorization code flow related client, the client base URL is <i>{oktaDomain}/oauth2/{authorizationServerId}</i>, where:
     * <ul>
     *     <li><b>oktaDomain</b> - string representation of the Okta domain</li>
     *     <li><b>authorizationServerId</b> - string representation of the Okta authorization server ID</li>
     * </ul>
     *
     * @return non-null string representation of the client base URL
     */
    @NotNull
    @Override
    public String getClientBaseUrl() {
        return "%s/oauth2/%s".formatted(this.oktaDomain, this.authorizationServerId);
    }

    /**
     * Returns the Okta domain which the client is pointing out to.
     *
     * @return non-null string representation of the Okta domain
     */
    public String getOktaDomain() {
        return this.oktaDomain;
    }

    /**
     * Returns the Okta authorization server ID domain which the client is pointing out to.
     *
     * @return non-null string representation of the Okta authorization server ID
     */
    public String getAuthorizationServerId() {
        return this.authorizationServerId;
    }

}
