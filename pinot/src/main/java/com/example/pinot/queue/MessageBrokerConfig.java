package com.example.pinot.queue;

import com.example.pinot.http_metric.HttpMetricProducer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MessageBrokerConfig {

    private static final @NotNull String HTTP_METRIC = "http-metric";

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public @NotNull ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public @NotNull KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(this.producerFactory());
    }

    @Bean
    public @NotNull NewTopic httpMetricsTopic() {
        return TopicBuilder.name(HTTP_METRIC)
                .build();
    }

    @Bean
    public @NotNull HttpMetricProducer httpMetricProducer(final @NotNull KafkaTemplate<String, Object> kafkaTemplate) {
        return new HttpMetricProducer(kafkaTemplate);
    }
}
