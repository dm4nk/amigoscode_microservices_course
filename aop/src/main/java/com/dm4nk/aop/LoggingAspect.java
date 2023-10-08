package com.dm4nk.aop;

import com.dm4nk.aop.annotations.ExcludeLog;
import com.dm4nk.aop.annotations.IncludeLog;
import com.dm4nk.aop.annotations.LogMethod;
import com.dm4nk.aop.annotations.Loggable;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.dm4nk.aop.Constants.DELIMITER;

@Slf4j
@Aspect
@Component
@NoArgsConstructor
class LoggingAspect {

    private static final Utils utils = new Utils(new ObjectMapper());

    @Pointcut(value = "@annotation(methodAnnotation)", argNames = "methodAnnotation")
    private void methodAnnotation(final LogMethod methodAnnotation) {
    }

    @AfterReturning(value = "methodAnnotation(methodAnnotation))", argNames = "joinPoint, methodAnnotation, result", returning = "result")
    private void logMethod(JoinPoint joinPoint, LogMethod methodAnnotation, Object result) {
        methodAnnotationLog(joinPoint, methodAnnotation.logResult(), methodAnnotation.level(), result);
    }

    @Pointcut("within(@com.dm4nk.aop.annotations.Loggable *)")
    private void classAnnotation() {
    }

    @AfterReturning(value = "classAnnotation()", argNames = "joinPoint, result", returning = "result")
    private void logClass(JoinPoint joinPoint, Object result) {
        classAnnotationLog(joinPoint, result);
    }

    private void classAnnotationLog(JoinPoint joinPoint, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogMethod logMethod = method.getAnnotation(LogMethod.class);
        if (logMethod != null)
            return;

        Loggable classAnnotation = method.getDeclaringClass().getAnnotation(Loggable.class);

        if (classAnnotation.excludeMethods()) {
            IncludeLog includeLog = method.getAnnotation(IncludeLog.class);

            if (includeLog != null) {
                methodAnnotationLog(joinPoint, true, classAnnotation.level(), result);
            }
        } else {
            ExcludeLog excludeLog = method.getAnnotation(ExcludeLog.class);

            if (excludeLog == null) {
                methodAnnotationLog(joinPoint, true, classAnnotation.level(), result);
            }
        }
    }

    private void methodAnnotationLog(JoinPoint joinPoint, boolean logResult, Level level, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String methodName = joinPoint.getTarget().getClass().getName() + "#" + codeSignature.getName();

        String args = StreamEx
                .zip(
                        codeSignature.getParameterNames(),
                        joinPoint.getArgs(),
                        utils::getParameterString
                )
                .zipWith(
                        Arrays.stream(signature.getMethod().getParameterAnnotations()).map(utils::isParameterIncluded)
                )
                .filterValues(Boolean::booleanValue)
                .keys()
                .collect(Collectors.joining(DELIMITER));

        if (logResult && signature.getReturnType() != void.class) {
            utils.log(methodName, args, result, level);
        } else {
            utils.log(methodName, args, level);
        }
    }
}
