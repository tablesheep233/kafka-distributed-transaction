package com.github.tablesheep233.tx.sample.pay.service;

import com.github.tablesheep233.tx.sample.pay.domain.Payment;
import com.github.tablesheep233.tx.sample.pay.repository.PaymentRepository;
import com.github.tablesheep233.tx.shared.constants.TopicNames;
import com.github.tablesheep233.tx.shared.domain.OrderPayMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PaymentService extends ApplicationObjectSupport {
    private final PaymentRepository paymentRepository;

    private final KafkaTemplate<Long, OrderPayMessage> kafkaTemplate;

    @Override
    protected boolean isContextRequired() {
        return true;
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
    public void pay(boolean payResult) {
        Payment payment = new Payment();
        payment.setOrderId(ThreadLocalRandom.current().nextLong());
        payment.setAmount(new BigDecimal(payment.getOrderId()));
        payment = paymentRepository.save(payment);

        getApplicationContext().publishEvent(new PayEvent(payment, payResult));
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    @Transactional(transactionManager = "kafkaTransactionManager", rollbackFor = Exception.class)
    public void listen(PayEvent event) {
        Payment payment = event.getPayment();
        CompletableFuture<SendResult<Long, OrderPayMessage>> sendResult =
                kafkaTemplate.send(TopicNames.ORDER_PAY_TOPIC, payment.getOrderId(), new OrderPayMessage(payment.getOrderId()));

        if (!event.isPayResult()) {
            throw new RuntimeException("pay error");
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PayEvent {
        private Payment payment;
        private boolean payResult;
    }
}
