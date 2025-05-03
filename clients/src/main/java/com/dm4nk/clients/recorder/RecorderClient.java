package com.dm4nk.clients.recorder;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "recorder",
        url = "${clients.recorder.url}",
        path = "${clients.recorder.path}"
)
public interface RecorderClient {
    @PostMapping
    ResponseEntity<Void> addAction(@RequestBody ActionRequest request);
}
