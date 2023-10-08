package com.dm4nk.aoptest;

import lombok.Data;

@Data
public class InnerTestObject {
    private final Long id;
    private final String name;

    @Override
    public String toString() {
        return "NO to string";
    }
}
