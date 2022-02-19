package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import org.jetbrains.annotations.NotNull;

public class GoogleAuthorizationCodeExchangeClient extends AbstractAuthorizationCodeExchangeClient {

    public GoogleAuthorizationCodeExchangeClient(@NotNull String clientId,
                                                 @NotNull String clientSecret,
                                                 @NotNull String redirectUri) {
        super(clientId, clientSecret, redirectUri);
    }

    @NotNull
    @Override
    public String getClientBaseUrl() {
        return "https://oauth2.googleapis.com";
    }

}
