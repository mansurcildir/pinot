package com.example.pinot.http_metric;

import static com.example.pinot.util.Constant.PATH;

import com.example.pinot.storage.StorageStrategy;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/http-metric")
@RequiredArgsConstructor
public class HttpMetricController {

  private final @NotNull StorageStrategy storageStrategy;

  @GetMapping("/hello")
  public @NotNull String hello() {
    return "Hello world!";
  }

  @PostMapping(
      path = "upload",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public void uploadFile(@RequestParam("file") final @NotNull MultipartFile file) throws Exception {

    final byte[] fileBytes = file.getInputStream().readAllBytes();
    this.storageStrategy.upload(PATH, fileBytes);
  }

  @GetMapping("/download")
  public @NotNull ResponseEntity<byte[]> downloadFile(@RequestParam final String path)
      throws Exception {
    final byte[] fileBytes = this.storageStrategy.download(PATH);

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + Path.of(path).getFileName() + "\"")
        .body(fileBytes);
  }
}
