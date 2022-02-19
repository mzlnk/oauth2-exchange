package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import org.jetbrains.annotations.NotNull;

public class GitHubAuthorizationCodeExchangeClient extends AbstractAuthorizationCodeExchangeClient {

    public GitHubAuthorizationCodeExchangeClient(@NotNull String clientId,
                                                 @NotNull String clientSecret,
                                                 @NotNull String redirectUri) {
        super(clientId, clientSecret, redirectUri);
    }

    @NotNull
    @Override
    public String getClientBaseUrl() {
        return "https://github.com";
    }

}
