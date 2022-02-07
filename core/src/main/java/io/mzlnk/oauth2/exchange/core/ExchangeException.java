package io.mzlnk.oauth2.exchange.core;

public class ExchangeException extends RuntimeException {

    private final String httpResponse;
    public ExchangeException(String message, String httpResponse) {
        super(message);
        this.httpResponse = httpResponse;
    }

    public String getHttpResponse() {
        return httpResponse;
    }

}
