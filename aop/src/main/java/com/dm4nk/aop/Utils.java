package com.dm4nk.aop;

import com.dm4nk.aop.annotations.ExcludeLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import static com.dm4nk.aop.Constants.ARG_FORMAT;
import static com.dm4nk.aop.Constants.PATTERN_WITHOUT_RESULT;
import static com.dm4nk.aop.Constants.PATTERN_WITH_RESULT;

@Slf4j
@AllArgsConstructor
class Utils {
    private final ObjectMapper objectMapper;

    void log(String methodName, String args, Level level) {
        log(String.format(PATTERN_WITHOUT_RESULT, methodName, args), level);
    }

    void log(String methodName, String args, Object result, Level level) {
        try {
            result = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            result = String.valueOf(result);
        }
        log(String.format(PATTERN_WITH_RESULT, methodName, args, result), level);
    }

    String getParameterString(String argumentName, Object argumentValue) {
        try {
            argumentValue = objectMapper.writeValueAsString(argumentValue);
        } catch (JsonProcessingException e) {
            argumentValue = String.valueOf(argumentValue);
        }
        return String.format(ARG_FORMAT, argumentName, argumentValue);
    }

    boolean isParameterIncluded(Annotation[] annotations) {
        return Arrays.stream(annotations).noneMatch(annotation -> annotation instanceof ExcludeLog);
    }

    private static void log(String message, Level level) {
        switch (level) {
            case INFO -> log.info(message);
            case DEBUG -> log.debug(message);
            case TRACE -> log.trace(message);
        }
    }
}
