package com.example.pinot;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PinotApplication {
  public static void main(final @NotNull String[] args) {
    SpringApplication.run(PinotApplication.class, args);
  }
}
