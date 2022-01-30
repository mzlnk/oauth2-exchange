package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

public interface AuthorizationCodeExchangeClient {

    String getClientId();

    String getClientSecret();

    String getRedirectUri();

    String getClientBaseUrl();

}
