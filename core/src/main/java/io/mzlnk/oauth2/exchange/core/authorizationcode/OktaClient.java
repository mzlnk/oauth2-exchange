package io.mzlnk.oauth2.exchange.core.authorizationcode;

public interface OktaClient {

    String getClientBaseUrl();

    class OktaSingleSignOnClient implements OktaClient {

        private final String oktaDomain;

        public OktaSingleSignOnClient(String oktaDomain) {
            this.oktaDomain = oktaDomain;
        }

        @Override
        public String getClientBaseUrl() {
            return "https://%s/oauth2".formatted(this.oktaDomain);
        }

    }

    class OktaAuthorizationServerClient implements OktaClient {

        private final String oktaDomain;
        private final String authorizationServerId;

        public OktaAuthorizationServerClient(String oktaDomain,
                                             String authorizationServerId) {
            this.oktaDomain = oktaDomain;
            this.authorizationServerId = authorizationServerId;
        }

        @Override
        public String getClientBaseUrl() {
            return "https://%s/oauth2/%s".formatted(this.oktaDomain, this.authorizationServerId);
        }

    }

}
