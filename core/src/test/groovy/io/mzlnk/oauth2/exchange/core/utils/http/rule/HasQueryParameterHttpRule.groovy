package io.mzlnk.oauth2.exchange.core.utils.http.rule

import okhttp3.Request

class HasQueryParameterHttpRule implements HttpRule {

    private final String key
    private final String expectedValue

    public HasQueryParameterHttpRule(String key, String expectedValue) {
        this.key = key
        this.expectedValue = expectedValue
    }

    @Override
    boolean matches(Request httpRequest) {
        return httpRequest.url().queryParameter(key) == expectedValue
    }

}
