package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

public abstract class OktaAuthorizationCodeExchangeClient extends AbstractAuthorizationCodeExchangeClient {

    protected OktaAuthorizationCodeExchangeClient(@NotNull String clientId,
                                                  @NotNull String clientSecret,
                                                  @NotNull String redirectUri) {
        super(clientId, clientSecret, redirectUri);
    }

    public static class OktaSingleSignOnClient extends OktaAuthorizationCodeExchangeClient {

        private final String oktaDomain;

        public OktaSingleSignOnClient(@NotNull String clientId,
                                      @NotNull String clientSecret,
                                      @NotNull String redirectUri,
                                      @NotNull String oktaDomain) {
            super(clientId, clientSecret, redirectUri);

            Preconditions.checkNotNull(oktaDomain,"Parameter `oktaDomain` cannot be null.");
            this.oktaDomain = oktaDomain;
        }

        @NotNull
        @Override
        public String getClientBaseUrl() {
            return "%s/oauth2".formatted(this.oktaDomain);
        }

    }

    public static class OktaAuthorizationServerClient extends OktaAuthorizationCodeExchangeClient {

        private final String oktaDomain;
        private final String authorizationServerId;

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

        @NotNull
        @Override
        public String getClientBaseUrl() {
            return "%s/oauth2/%s".formatted(this.oktaDomain, this.authorizationServerId);
        }

    }

}
