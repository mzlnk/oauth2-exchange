package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import org.jetbrains.annotations.NotNull;

public final class FacebookAuthorizationCodeExchangeClient extends AbstractAuthorizationCodeExchangeClient {

    public FacebookAuthorizationCodeExchangeClient(@NotNull String clientId,
                                                   @NotNull String clientSecret,
                                                   @NotNull String redirectUri) {
        super(clientId, clientSecret, redirectUri);
    }

    @NotNull
    @Override
    public String getClientBaseUrl() {
        return "https://graph.facebook.com";
    }

}
