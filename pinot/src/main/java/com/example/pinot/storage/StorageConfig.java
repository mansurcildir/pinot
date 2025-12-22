package com.example.pinot.storage;

import io.minio.MinioClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {
  private static final String S3_STRATEGY = "s3";

  @Value("${storage.strategy}")
  private String storageType;

  @Value("${storage.s3.base-url}")
  private String baseUrl;

  @Value("${storage.s3.credentials.access-key}")
  private String accessKey;

  @Value("${storage.s3.credentials.secret-key}")
  private String secretKey;

  @Bean
  public @Nullable StorageStrategy storageStrategy(final @NotNull StorageStrategy s3) {
    if (S3_STRATEGY.equals(this.storageType)) {
      return s3;
    }
    return null;
  }

  @Bean
  public StorageStrategy s3(final @NotNull MinioClient minioClient) {
    return new S3(minioClient);
  }

  @Bean
  public @NotNull MinioClient minioClient() {
    return MinioClient.builder()
        .endpoint(this.baseUrl)
        .credentials(this.accessKey, this.secretKey)
        .build();
  }
}
