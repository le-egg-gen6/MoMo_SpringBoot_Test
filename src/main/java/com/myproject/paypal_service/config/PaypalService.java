package com.myproject.paypal_service.config;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

/**
 * @author nguyenle
 * @since 9:30 PM Tue 9/10/2024
 */
public interface PaypalService {

    Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl
    );

    Payment executePayment(
            String paymentId,
            String payerId
    );

}
