package com.github.tablesheep233.tx.sample.pay.api;

import com.github.tablesheep233.tx.sample.pay.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pay")
@RequiredArgsConstructor
public class PaymentApi {

    private final PaymentService paymentService;

    @PostMapping
    public boolean pay(@RequestBody boolean payResult) {
        paymentService.pay(payResult);
        return payResult;
    }

}
