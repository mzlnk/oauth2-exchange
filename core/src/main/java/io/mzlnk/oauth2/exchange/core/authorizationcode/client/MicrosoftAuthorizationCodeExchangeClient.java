package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

public abstract class MicrosoftAuthorizationCodeExchangeClient extends AbstractAuthorizationCodeExchangeClient {

    public MicrosoftAuthorizationCodeExchangeClient(@NotNull String clientId,
                                                    @NotNull String clientSecret,
                                                    @NotNull String redirectUri) {
        super(clientId, clientSecret, redirectUri);
    }

    public static class MicrosoftCommonClient extends MicrosoftAuthorizationCodeExchangeClient {

        public MicrosoftCommonClient(@NotNull String clientId,
                                     @NotNull String clientSecret,
                                     @NotNull String redirectUri) {
            super(clientId, clientSecret, redirectUri);
        }

        @NotNull
        @Override
        public String getClientBaseUrl() {
            return "https://login.microsoftonline.com/common";
        }
    }

    public static class MicrosoftOrganizationClient extends MicrosoftAuthorizationCodeExchangeClient {

        public MicrosoftOrganizationClient(@NotNull String clientId,
                                           @NotNull String clientSecret,
                                           @NotNull String redirectUri) {
            super(clientId, clientSecret, redirectUri);
        }

        @NotNull
        @Override
        public String getClientBaseUrl() {
            return "https://login.microsoftonline.com/organizations";
        }

    }

    public static class MicrosoftConsumerClient extends MicrosoftAuthorizationCodeExchangeClient {

        public MicrosoftConsumerClient(@NotNull String clientId,
                                       @NotNull String clientSecret,
                                       @NotNull String redirectUri) {
            super(clientId, clientSecret, redirectUri);
        }

        @NotNull
        @Override
        public String getClientBaseUrl() {
            return "https://login.microsoftonline.com/consumers";
        }

    }

    public static class MicrosoftAzureADClient extends MicrosoftAuthorizationCodeExchangeClient {

        private final String azureADId;

        public MicrosoftAzureADClient(@NotNull String clientId,
                                      @NotNull String clientSecret,
                                      @NotNull String redirectUri,
                                      @NotNull String azureADId) {
            super(clientId, clientSecret, redirectUri);

            Preconditions.checkNotNull(azureADId, "Parameter `azureADId` cannot be null.");
            this.azureADId = azureADId;
        }

        @NotNull
        @Override
        public String getClientBaseUrl() {
            return "https://login.microsoftonline.com/%s".formatted(this.azureADId);
        }

    }

}
