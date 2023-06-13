package com.github.tablesheep233.tx.sample.pay;

import com.github.tablesheep233.tx.shared.constants.TopicNames;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

/**
 * The type Account application.
 *
 * @author tablesheep233
 */
@SpringBootApplication
public class PayApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }

    @Bean
    public NewTopic orderPayTopic() {
        return TopicBuilder.name(TopicNames.ORDER_PAY_TOPIC)
                .partitions(3)
                .replicas(3)
                .build();
    }
}
