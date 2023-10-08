package com.dm4nk.aoptest;


import com.dm4nk.aop.logger.Level;
import com.dm4nk.aop.logger.annotations.ExcludeLog;
import com.dm4nk.aop.logger.annotations.LogMethod;
import com.dm4nk.aop.logger.annotations.Loggable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Loggable(level = Level.INFO)
@AllArgsConstructor
public class AopTestService {
    private final Util util;

    public void test(String name1, String name2, Integer integer) {
        log.info("aaa");
        TestObject testObject = new TestObject(
                1L,
                name1,
                new InnerTestObject(
                        2L,
                        name2
                )
        );

        util.testUtil("b");

    }
}
