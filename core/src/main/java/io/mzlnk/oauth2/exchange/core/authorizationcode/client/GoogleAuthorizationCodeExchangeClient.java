package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

public class GoogleAuthorizationCodeExchangeClient extends AbstractAuthorizationCodeExchangeClient {

    public GoogleAuthorizationCodeExchangeClient(String clientId,
                                                 String clientSecret,
                                                 String redirectUri) {
        super(clientId, clientSecret, redirectUri);
    }

    @Override
    public String getClientBaseUrl() {
        return "https://oauth2.googleapis.com";
    }
}
