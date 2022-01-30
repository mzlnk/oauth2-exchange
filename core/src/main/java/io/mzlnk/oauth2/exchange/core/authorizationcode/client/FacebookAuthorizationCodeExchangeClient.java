package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

public class FacebookAuthorizationCodeExchangeClient extends AbstractAuthorizationCodeExchangeClient {

    public FacebookAuthorizationCodeExchangeClient(String clientId,
                                                   String clientSecret,
                                                   String redirectUri) {
        super(clientId, clientSecret, redirectUri);
    }

    @Override
    public String getClientBaseUrl() {
        return "https://graph.facebook.com";
    }

}
