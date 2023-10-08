package com.dm4nk.aoptest;

import com.dm4nk.aop.Level;
import com.dm4nk.aop.annotations.ExcludeLog;
import com.dm4nk.aop.annotations.LogMethod;
import com.dm4nk.aop.annotations.Loggable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Loggable(level = Level.DEBUG, excludeMethods = true)
public class AopTestService {

    @LogMethod(logResult = false, level = Level.INFO)
    public TestObject test(String name, @ExcludeLog String name2, Integer integer) {
        log.info("test");

        TestObject testObject = new TestObject(
                1L,
                name,
                new InnerTestObject(
                        2L,
                        "inner_" + name
                )
        );

        return testObject;
    }
}
