package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

public abstract class MicrosoftAuthorizationCodeExchangeClient extends AbstractAuthorizationCodeExchangeClient {

    public MicrosoftAuthorizationCodeExchangeClient(String clientId,
                                                    String clientSecret,
                                                    String redirectUri) {
        super(clientId, clientSecret, redirectUri);
    }

    public static class MicrosoftCommonClient extends MicrosoftAuthorizationCodeExchangeClient {

        public MicrosoftCommonClient(String clientId,
                                     String clientSecret,
                                     String redirectUri) {
            super(clientId, clientSecret, redirectUri);
        }

        @Override
        public String getClientBaseUrl() {
            return "https://login.microsoftonline.com/common";
        }
    }

    public static class MicrosoftOrganizationClient extends MicrosoftAuthorizationCodeExchangeClient {

        public MicrosoftOrganizationClient(String clientId,
                                           String clientSecret,
                                           String redirectUri) {
            super(clientId, clientSecret, redirectUri);
        }

        @Override
        public String getClientBaseUrl() {
            return "https://login.microsoftonline.com/organizations";
        }

    }

    public static class MicrosoftConsumerClient extends MicrosoftAuthorizationCodeExchangeClient {

        public MicrosoftConsumerClient(String clientId,
                                       String clientSecret,
                                       String redirectUri) {
            super(clientId, clientSecret, redirectUri);
        }

        @Override
        public String getClientBaseUrl() {
            return "https://login.microsoftonline.com/consumers";
        }

    }

    public static class MicrosoftAzureADClient extends MicrosoftAuthorizationCodeExchangeClient {

        private final String azureADId;

        public MicrosoftAzureADClient(String clientId,
                                      String clientSecret,
                                      String redirectUri,
                                      String azureADId) {
            super(clientId, clientSecret, redirectUri);
            this.azureADId = azureADId;
        }

        @Override
        public String getClientBaseUrl() {
            return "https://login.microsoftonline.com/%s".formatted(this.azureADId);
        }

    }

}
