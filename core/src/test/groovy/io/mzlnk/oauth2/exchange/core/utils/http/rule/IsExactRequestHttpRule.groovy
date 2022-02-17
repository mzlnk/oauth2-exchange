package io.mzlnk.oauth2.exchange.core.utils.http.rule

import okhttp3.Request

import java.util.regex.Pattern

class IsExactRequestHttpRule implements HttpRule {

    private final String expectedHttpMethod
    private final Pattern expectedUrl

    IsExactRequestHttpRule(String expectedHttpMethod, String expectedUrl) {
        this.expectedHttpMethod = expectedHttpMethod
        this.expectedUrl = ~"${expectedUrl}(\\?.*+)?+"
    }

    @Override
    boolean matches(Request httpRequest) {
        return httpRequest.method() == expectedHttpMethod && httpRequest.url().url().toString().matches(expectedUrl)
    }
}
