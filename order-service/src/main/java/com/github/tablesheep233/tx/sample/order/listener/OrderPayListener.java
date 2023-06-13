package com.github.tablesheep233.tx.sample.order.listener;

import com.github.tablesheep233.tx.sample.order.domain.Order;
import com.github.tablesheep233.tx.sample.order.repository.OrderRepository;
import com.github.tablesheep233.tx.shared.constants.TopicNames;
import com.github.tablesheep233.tx.shared.domain.OrderPayMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderPayListener implements AcknowledgingMessageListener<Long, OrderPayMessage> {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = {TopicNames.ORDER_PAY_TOPIC}, groupId = "1")
    @Override
    public void onMessage(ConsumerRecord<Long, OrderPayMessage> consumerRecord, Acknowledgment acknowledgment) {
        try {
            OrderPayMessage orderPayMessage = consumerRecord.value();
            log.info(orderPayMessage.toString());
            Order order = new Order();
            order.setAmount(new BigDecimal(orderPayMessage.getOrderId()));
            order.setId(orderPayMessage.getOrderId());
            order.setStatus("PAYED");
            orderRepository.save(order);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            acknowledgment.nack(Duration.ofMillis(0));
        }
    }
}
