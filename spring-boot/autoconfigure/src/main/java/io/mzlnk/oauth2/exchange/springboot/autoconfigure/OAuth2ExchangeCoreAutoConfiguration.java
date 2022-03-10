package io.mzlnk.oauth2.exchange.springboot.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuth2ExchangeCoreAutoConfiguration {

    private final Logger log = LoggerFactory.getLogger(OAuth2ExchangeCoreAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public OkHttpClient okHttpClient() {
        log.debug("No {} bean found - creating one", OkHttpClient.class.getName());
        return new OkHttpClient();
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        log.debug("No {} bean found - creating one", ObjectMapper.class.getName());
        return new ObjectMapper();
    }

}
