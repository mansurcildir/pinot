package com.example.pinot.storage;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

@Slf4j
@RequiredArgsConstructor
public class S3 implements StorageStrategy {
  private final @NotNull MinioClient minioClient;

  @Value("${storage.s3.bucket}")
  private String bucket;

  @Override
  public void upload(final @NotNull String path, final byte[] bytes) throws Exception {
    try (final InputStream inputStream = new ByteArrayInputStream(bytes)) {
      this.minioClient.putObject(
          PutObjectArgs.builder().bucket(this.bucket).object(path).stream(
                  inputStream, bytes.length, -1)
              .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)
              .build());
    }
  }

  @Override
  public byte[] download(final @NotNull String path) throws Exception {
    try (final InputStream stream =
        this.minioClient.getObject(
            GetObjectArgs.builder().bucket(this.bucket).object(path).build())) {
      return stream.readAllBytes();
    }
  }
}
