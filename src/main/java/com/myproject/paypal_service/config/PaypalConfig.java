package com.myproject.paypal_service.config;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author nguyenle
 * @since 9:27 PM Tue 9/10/2024
 */
@Configuration
public class PaypalConfig {

    @Value("${paypal.client-id}")
    private String paypalClientId;

    @Value("${paypal.client-secret}")
    private String paypalClientSecret;

    @Value("${paypal.mode}")
    private String paypalMode;

    @Bean
    public APIContext apiContext() {
        return new APIContext(paypalClientId, paypalClientSecret, paypalMode);
    }

}
