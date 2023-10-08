package com.dm4nk.aoptest;

import lombok.Data;

@Data
public class TestObject {
    private final Long id;
    private final String name;
    private final InnerTestObject inner;


    @Override
    public String toString() {
        return "NO to string";
    }
}
