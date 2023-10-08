package com.dm4nk.aoptest;


import com.dm4nk.aop.logger.Level;
import com.dm4nk.aop.logger.annotations.LogMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Util {

    @LogMethod(level = Level.INFO)
    public void testUtil(String a) {
        log.info(a);
    }
}
