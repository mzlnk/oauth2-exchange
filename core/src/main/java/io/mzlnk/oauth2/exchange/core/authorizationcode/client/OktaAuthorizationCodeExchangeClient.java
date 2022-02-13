package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

public abstract class OktaAuthorizationCodeExchangeClient extends AbstractAuthorizationCodeExchangeClient {

    protected OktaAuthorizationCodeExchangeClient(String clientId,
                                                  String clientSecret,
                                                  String redirectUri) {
        super(clientId, clientSecret, redirectUri);
    }

    public static class OktaSingleSignOnClient extends OktaAuthorizationCodeExchangeClient {

        private final String oktaDomain;

        public OktaSingleSignOnClient(String clientId,
                                      String clientSecret,
                                      String redirectUri,
                                      String oktaDomain) {
            super(clientId, clientSecret, redirectUri);
            this.oktaDomain = oktaDomain;
        }

        @Override
        public String getClientBaseUrl() {
            return "%s/oauth2".formatted(this.oktaDomain);
        }

    }

    public static class OktaAuthorizationServerClient extends OktaAuthorizationCodeExchangeClient {

        private final String oktaDomain;
        private final String authorizationServerId;

        public OktaAuthorizationServerClient(String clientId,
                                             String clientSecret,
                                             String redirectUri,
                                             String oktaDomain,
                                             String authorizationServerId) {
            super(clientId, clientSecret, redirectUri);
            this.oktaDomain = oktaDomain;
            this.authorizationServerId = authorizationServerId;
        }

        @Override
        public String getClientBaseUrl() {
            return "%s/oauth2/%s".formatted(this.oktaDomain, this.authorizationServerId);
        }

    }

}
