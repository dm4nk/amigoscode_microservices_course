package com.dm4nk.aoptest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AopTestController {
    private final AopTestService aopTestService;

    @GetMapping("/api/v1/aop/test")
    public void test() {
        aopTestService.test("AOP", "none", 2);
    }
}
