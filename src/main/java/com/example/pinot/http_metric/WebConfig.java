package com.example.pinot.http_metric;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final @NotNull MetricsInterceptor metricsInterceptor;

  public WebConfig(final @NotNull MetricsInterceptor metricsInterceptor) {
    this.metricsInterceptor = metricsInterceptor;
  }

  @Override
  public void addInterceptors(final @NotNull InterceptorRegistry registry) {
    registry
        .addInterceptor(this.metricsInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**");
  }
}
