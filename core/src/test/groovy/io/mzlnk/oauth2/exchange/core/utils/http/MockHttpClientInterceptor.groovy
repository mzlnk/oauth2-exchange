package io.mzlnk.oauth2.exchange.core.utils.http

import io.mzlnk.oauth2.exchange.core.utils.http.rule.HttpRule
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import org.jetbrains.annotations.NotNull

class MockHttpClientInterceptor implements Interceptor {

    private List<ApplicableHttpRule> rules = []
    private HttpResponse response

    @Override
    Response intercept(@NotNull Chain chain) throws IOException {
        def httpRequest = chain.request();
        def ruleToApply = this.rules.find {it.rules.every {it.matches(httpRequest)}}

        return ruleToApply?.httpResponse
    }

    RuleBuilder when(HttpRule... rules) {
        return new RuleBuilder(rules as List<HttpRule>)
    }

    class RuleBuilder {

        private List<HttpRule> rulesToAdd

        RuleBuilder(List<HttpRule> rulesToAdd) {
            this.rulesToAdd = rulesToAdd
        }

        MockHttpClientInterceptor thenReturn(Response httpResponse) {
            MockHttpClientInterceptor.this.rules.add(new ApplicableHttpRule(rulesToAdd, httpResponse))
            return MockHttpClientInterceptor.this
        }

    }

    private static class ApplicableHttpRule {

        private final List<HttpRule> rules
        private final Response httpResponse

        ApplicableHttpRule(List<HttpRule> rules, Response httpResponse) {
            this.rules = rules
            this.httpResponse = httpResponse
        }

    }

}
