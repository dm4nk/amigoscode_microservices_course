package com.dm4nk.ban.controller;

import com.dm4nk.ban.service.BanService;
import com.dm4nk.clients.ban.BanCheckResponse;
import com.dm4nk.clients.ban.BanRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

import static com.dm4nk.ban.common.Constants.Web.API;
import static com.dm4nk.ban.common.Constants.Web.BAN;
import static com.dm4nk.ban.common.Constants.Web.BAN_CHECK;
import static com.dm4nk.ban.common.Constants.Web.V1;

@RestController
@RequestMapping(API + V1)
@Slf4j
public record BanController(BanService banService) {

    @PostMapping(path = BAN)
    public ResponseEntity<String> ban(@RequestBody BanRequest banRequest) {
        UUID customerId = banService.ban(banRequest);
        log.info("Banned customer, id: {}", customerId);
        return ResponseEntity
                .created(URI.create(API + V1 + BAN_CHECK + customerId))
                .body(String.format("Customer id=%s banned", customerId));
    }

    @GetMapping(path = BAN_CHECK + "{customerId}")
    public BanCheckResponse isBanned(@PathVariable("customerId") UUID customerId) {
        Boolean isBannedCustomer = banService.isBannedCustomer(customerId);
        log.info("ban check request for customer {}", customerId);
        return new BanCheckResponse(isBannedCustomer);
    }

}
