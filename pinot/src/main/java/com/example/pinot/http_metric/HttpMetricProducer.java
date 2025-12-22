package com.example.pinot.http_metric;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HttpMetricProducer {

    private final @NotNull KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(final @NotNull String topic, final @NotNull HttpMetric metric) {
        this.kafkaTemplate.send(topic, metric);
    }
}
