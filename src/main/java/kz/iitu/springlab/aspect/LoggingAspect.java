package kz.iitu.springlab.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
@Component
@Order(2)
public class LoggingAspect {

    private static final Logger log =
            Logger.getLogger(LoggingAspect.class.getName());

    @Before("kz.iitu.springlab.aspect.Pointcuts.serviceOperation()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("[LOG] -> "
                + joinPoint.getSignature().toShortString()
                + " args="
                + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(
            pointcut = "kz.iitu.springlab.aspect.Pointcuts.serviceOperation()",
            returning = "result"
    )
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("[LOG] <- "
                + joinPoint.getSignature().toShortString()
                + " returned "
                + result);
    }

    @AfterThrowing(
            pointcut = "kz.iitu.springlab.aspect.Pointcuts.serviceOperation()",
            throwing = "ex"
    )
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.severe("[LOG] !! "
                + joinPoint.getSignature().toShortString()
                + " threw "
                + ex.getClass().getSimpleName()
                + ": "
                + ex.getMessage());
    }
}