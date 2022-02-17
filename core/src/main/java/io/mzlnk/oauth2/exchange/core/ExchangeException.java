package io.mzlnk.oauth2.exchange.core;

import okhttp3.Response;

public class ExchangeException extends RuntimeException {

    private final Response response;

    public ExchangeException(String message, Response response) {
        super(message);
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

}
