package io.mzlnk.oauth2.exchange.springboot.demo.api;

import io.mzlnk.oauth2.exchange.core.authorizationcode.GoogleAuthorizationCodeExchange;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.OAuth2TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class OAuth2Controller {

    private final GoogleAuthorizationCodeExchange googleExchange;

    public OAuth2Controller(GoogleAuthorizationCodeExchange googleExchange) {
        this.googleExchange = googleExchange;
    }

    @GetMapping("/callback/google")
    public ResponseEntity<TokenDetails> googleOAuth2Callback(@RequestParam String code) {
        var tokenResponse = googleExchange.exchangeAuthorizationCode(code);
        return ResponseEntity.ok(new TokenDetails(tokenResponse));
    }

    private static record TokenDetails(String token) {

        public TokenDetails(OAuth2TokenResponse tokenResponse) {
            this(tokenResponse.getAccessToken());
        }

    }

}
