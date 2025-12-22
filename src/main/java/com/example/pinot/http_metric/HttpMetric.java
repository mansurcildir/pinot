package com.example.pinot.http_metric;

import lombok.Builder;
import org.jetbrains.annotations.NotNull;

@Builder
public record HttpMetric(@NotNull String path, int status, long duration, long timestamp) {}
