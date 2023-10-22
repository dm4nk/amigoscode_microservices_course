package com.dm4nk.clients.fraud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name="fraud",
        url = "${clients.fraud.url}"
)
public interface FraudClient {

    @GetMapping(path = "/api/vi/fraud-check/{customerId}")
    FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId);

}
