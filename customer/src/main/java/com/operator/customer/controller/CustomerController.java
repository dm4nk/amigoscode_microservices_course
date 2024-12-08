package com.operator.customer.controller;

import com.operator.clients.customer.ChangeTarifPlanRequest;
import com.operator.clients.customer.CustomerRequest;
import com.operator.clients.customer.CustomerResponse;
import com.operator.customer.service.CustomerService;
import com.operator.customer.service.TarifPlanService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final TarifPlanService tarifPlanService;

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<CustomerResponse>> findCustomers(@PathVariable(name = "name") String name) {
        return customerService.findAllByName(name);
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest request) {
        return customerService.create(request);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody CustomerRequest request, @PathVariable UUID id) {
        return customerService.update(id, request);
    }

    @PostMapping("/plan")
    public ResponseEntity<Void> changeTarifPlan(@RequestBody ChangeTarifPlanRequest tarifPlanRequest) {
        return tarifPlanService.changePlan(tarifPlanRequest);
    }
}
