package com.dm4nk.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.dm4nk.aop.Constants.*;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class LoggingAspect {

    private final ObjectMapper objectMapper;

    @Pointcut(value = "@annotation(logAnnotation)", argNames = "logAnnotation")
    void annotation(final Loggable logAnnotation) {
    }

    @AfterReturning(value = "annotation(logAnnotation))", argNames = "joinPoint, logAnnotation, result", returning = "result")
    private void logWithAnnotation(JoinPoint joinPoint, Loggable logAnnotation, Object result) {
        genericLog(joinPoint, logAnnotation, result);
    }

    private void genericLog(JoinPoint joinPoint, Loggable methodAnnotation, Object result) {
        boolean logResult = methodAnnotation.logResult();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        List<Boolean> includeMethod = Arrays.stream(signature.getMethod().getParameterAnnotations())
                .map(LoggingAspect::isParameterIncluded)
                .toList();

        // todo check .allMatch and return from function

        List<String> args = StreamEx
                .zip(
                        codeSignature.getParameterNames(),
                        joinPoint.getArgs(),
                        (name, value) -> String.format(ARG_FORMAT, name, value))
                .toList();

        String argsString = StreamEx
                .zip(args, includeMethod, ImmutablePair::of)
                .filter(ImmutablePair::getRight)
                .map(ImmutablePair::getLeft)
                .collect(Collectors.joining(DELIMITER));

        String methodName = codeSignature.getName();

        if (logResult) {
            try {
                log.info(PATTERN_WITH_RESULT, methodName, argsString, objectMapper.writeValueAsString(result));
            } catch (JsonProcessingException e) {
                log.info(PATTERN_WITH_RESULT, methodName, argsString, Objects.toString(result, "- "));
            }
        } else {
            log.info(PATTERN_WITHOUT_RESULT, methodName, argsString);
        }
    }

    private static Boolean isParameterIncluded(Annotation[] annotations) {
        return Arrays.stream(annotations).noneMatch(annotation -> annotation instanceof Exclude);
    }
}
