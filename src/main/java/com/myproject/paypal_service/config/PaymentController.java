package com.myproject.paypal_service.config;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author nguyenle
 * @since 9:33 PM Tue 9/10/2024
 */
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaypalService paypalService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/payment/create")
    public RedirectView createPayment(
            @RequestParam("method") String method,
            @RequestParam("amount") String amount,
            @RequestParam("currency") String currency,
            @RequestParam("description") String description
    ) {
        String cancelUrl = "http://localhost:8080/payment/cancel";
        String successUrl = "http://localhost:8080/payment/success";
        Payment payment = paypalService.createPayment(
                Double.valueOf(amount),
                currency,
                method,
                "sale",
                description,
                cancelUrl,
                successUrl
        );
        if (payment == null) {
            return new RedirectView("/payment/error");
        }
        for (Links links : payment.getLinks()) {
            if (links.getRel().equals("approval_url")) {
                return new RedirectView(links.getHref());
            }
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/payment/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("payerId") String payerId
    ) {

        Payment payment = paypalService.executePayment(paymentId, payerId);
        if (payment == null) {
            return "paymentError";
        }
        if (payment.getState().equals("approved")) {
            return "paymentSuccess";
        }
        return "paymentError";
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel() {
        return "paymentCancel";
    }

    @GetMapping("/payment/error")
    public String paymentError() {
        return "paymentError";
    }

}
