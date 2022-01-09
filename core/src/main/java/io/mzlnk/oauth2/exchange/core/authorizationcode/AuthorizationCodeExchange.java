package io.mzlnk.oauth2.exchange.core.authorizationcode;

import java.util.Map;

public interface AuthorizationCodeExchange<R extends Map<String, Object>> {

    R exchangeAuthorizationCode(String code);

}
