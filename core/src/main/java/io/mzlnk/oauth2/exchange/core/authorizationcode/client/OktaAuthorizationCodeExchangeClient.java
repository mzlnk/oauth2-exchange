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
            return "https://%s/oauth2".formatted(this.oktaDomain);
        }

    }

}
