package com.example.pinot.http_metric;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class MetricsInterceptor implements HandlerInterceptor {

    private static final @NotNull String HTTP_METRIC = "http-metric";
    private final @NotNull HttpMetricProducer httpMetricProducer;

    @Override
    public boolean preHandle(final @NotNull HttpServletRequest request,
                             final @NotNull HttpServletResponse response,
                             final @NotNull Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(final @NotNull HttpServletRequest request,
                                final @NotNull HttpServletResponse response,
                                final @NotNull Object handler,
                                final @Nullable Exception ex) {
        final long duration = System.currentTimeMillis() - (Long) request.getAttribute("startTime");
        final HttpMetric metric = HttpMetric.builder()
                .path(request.getRequestURI())
                .status(response.getStatus())
                .duration(duration)
                .timestamp(System.currentTimeMillis())
                .build();

        this.httpMetricProducer.sendMessage(HTTP_METRIC, metric);
    }
}
