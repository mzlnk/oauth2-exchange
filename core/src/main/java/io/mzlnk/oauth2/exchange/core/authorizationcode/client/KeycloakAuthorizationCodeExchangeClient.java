package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an implementation of {@link AuthorizationCodeExchangeClient} for Keycloak OAuth2 authorization code flow.
 * Implementation of the client is created based on information posted on official
 * <a href="https://www.keycloak.org/docs/latest/server_admin/">documentation site</a>.
 */
public final class KeycloakAuthorizationCodeExchangeClient extends AbstractAuthorizationCodeExchangeClient {

    private final String host;
    private final String realm;

    /**
     * Construct an instance with given client ID, client secret (associated with the client ID), redirection URI, host and realm.
     *
     * @param clientId     non-null string representation of the client ID
     * @param clientSecret non-null string representation of the client secret
     * @param redirectUri  non-null string representation of the redirection URI
     * @param host         non-null string representation of the host
     * @param realm        non-null string representation of the realm
     * @throws NullPointerException if any of the parameters is null
     */
    public KeycloakAuthorizationCodeExchangeClient(@NotNull String clientId,
                                                   @NotNull String clientSecret,
                                                   @NotNull String redirectUri,
                                                   @NotNull String host,
                                                   @NotNull String realm) {
        super(clientId, clientSecret, redirectUri);

        Preconditions.checkNotNull(host, "Parameter `host` cannot be null.");
        Preconditions.checkNotNull(realm, "Parameter `realm` cannot be null.");

        this.host = host;
        this.realm = realm;
    }

    /**
     * Returns the client base URL which the request for exchange authorization code for a token is sent to. For Keycloak
     * OAuth2 authorization code flow, the client base URL is <i>{host}/auth/realms/{realm}</i> where
     * <ul>
     *     <li><b>host</b> - string representation of the host provided during instance initialization</li>
     *     <li><b>realm</b> - string representation of the realm provided during instance initialization</li>
     * </ul>
     *
     * @return non-null string representation of the client base URL
     */
    @NotNull
    @Override
    public String getClientBaseUrl() {
        return "%s/auth/realms/%s".formatted(this.host, this.realm);
    }

    /**
     * Returns the host which the client is pointing out to.
     *
     * @return non-null string representation of the host
     */
    @NotNull
    public String getHost() {
        return this.host;
    }

    /**
     * Returns the realm which the client is pointing out to.
     *
     * @return non-null string representation of the realm
     */
    @NotNull
    public String getRealm() {
        return this.realm;
    }

}
