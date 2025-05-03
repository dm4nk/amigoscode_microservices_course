package com.dm4nk.customer.aop;


import com.dm4nk.clients.recorder.ActionRequest;
import com.dm4nk.clients.recorder.RecorderClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
class EventAspect {

    private final RecorderClient recorderClient;

    @Pointcut(value = "@annotation(methodAnnotation)", argNames = "methodAnnotation")
    private void methodAnnotation(final AuditEvent methodAnnotation) {
    }

    @Before(value = "methodAnnotation(methodAnnotation))", argNames = "joinPoint, methodAnnotation")
    private void recordMethod(JoinPoint joinPoint, AuditEvent methodAnnotation) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            recorderClient.addAction(new ActionRequest(userName, methodAnnotation.value()));
        } catch (Exception e) {
            log.warn("Could not add action to recorder client: {}:{}", userName, methodAnnotation.value());
        }
    }
}
