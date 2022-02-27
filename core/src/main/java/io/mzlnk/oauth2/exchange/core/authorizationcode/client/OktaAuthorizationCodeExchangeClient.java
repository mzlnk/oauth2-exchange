package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import static io.mzlnk.oauth2.exchange.core.authorizationcode.client.OktaAuthorizationCodeExchangeClient.OktaAuthorizationServerClient;
import static io.mzlnk.oauth2.exchange.core.authorizationcode.client.OktaAuthorizationCodeExchangeClient.OktaSingleSignOnClient;

/**
 * Represents a template for all types of clients available for OAuth2 authorization code flow for Okta identity provider.<br />
 * Currently, there are available 2 different clients:
 * <ul>
 *     <li>{@link OktaSingleSignOnClient}</li>
 *     <li>{@link OktaAuthorizationServerClient}</li>
 * </ul>
 * All implementations are created based on information found posted on official
 * <a href="https://developer.okta.com/docs/reference/api/oidc/#_2-okta-as-the-identity-platform-for-your-app-or-api">documentation site</a>.
 */
public sealed abstract class OktaAuthorizationCodeExchangeClient
        extends AbstractAuthorizationCodeExchangeClient
        permits OktaSingleSignOnClient, OktaAuthorizationServerClient {

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

    /**
     * Represents an implementation of {@link AuthorizationCodeExchangeClient} for Okta OAuth2 authorization code flow.
     * This type of the client is dedicated for use case where your users are all part of your Okta organization and
     * you would just like to offer them single sign-on
     * <br />
     * Implementation of the client is created based on information posted on official
     * <a href="https://developer.okta.com/docs/reference/api/oidc/">documentation site</a>.
     */
    public static final class OktaSingleSignOnClient extends OktaAuthorizationCodeExchangeClient {

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
        public OktaSingleSignOnClient(@NotNull String clientId,
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

    /**
     * Represents an implementation of {@link AuthorizationCodeExchangeClient} for Okta OAuth2 authorization code flow.
     * This type of the client is dedicated for use case where Okta is the authorization server for your resource server
     * <br />
     * Implementation of the client is created based on information posted on official
     * <a href="https://developer.okta.com/docs/reference/api/oidc/">documentation site</a>.
     */
    public static final class OktaAuthorizationServerClient extends OktaAuthorizationCodeExchangeClient {

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
        public OktaAuthorizationServerClient(@NotNull String clientId,
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

}
