package io.mzlnk.oauth2.exchange.core.authorizationcode;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface AuthorizationCodeExchange<R extends Map<String, Object>> {

    R exchangeAuthorizationCode(@NotNull String code);

}
