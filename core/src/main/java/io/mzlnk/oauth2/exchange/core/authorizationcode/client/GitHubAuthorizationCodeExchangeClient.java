package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

public class GitHubAuthorizationCodeExchangeClient extends AbstractAuthorizationCodeExchangeClient {

    public GitHubAuthorizationCodeExchangeClient(String clientId,
                                                 String clientSecret,
                                                 String redirectUri) {
        super(clientId, clientSecret, redirectUri);
    }

    @Override
    public String getClientBaseUrl() {
        return "https://github.com";
    }

}
