package com.example.pinot.storage;

import org.jetbrains.annotations.NotNull;

public interface StorageStrategy {
  void upload(@NotNull String path, byte[] bytes) throws Exception;
  byte[] download(@NotNull String path) throws Exception;
}
