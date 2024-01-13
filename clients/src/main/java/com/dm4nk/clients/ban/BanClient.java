package com.dm4nk.clients.ban;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(
        name = "ban",
        url = "${clients.ban.url}"
)
public interface BanClient {
    @GetMapping(path = "/api/v1/ban")
    ResponseEntity ban(@RequestBody BanRequest banRequest);

    @GetMapping(path = "/api/v1/ban-check/{customerId}")
    BanCheckResponse isBanned(@PathVariable("customerId") UUID customerId);
}
