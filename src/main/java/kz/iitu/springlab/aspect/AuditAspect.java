package kz.iitu.springlab.aspect;

import kz.iitu.springlab.audit.Audited;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
@Component
@Order(1)
public class AuditAspect {

    private static final Logger log =
            Logger.getLogger(AuditAspect.class.getName());

    @Around("@annotation(audited)")
    public Object audit(ProceedingJoinPoint pjp, Audited audited) throws Throwable {

        String action = audited.action();

        if (audited.logArguments()) {
            log.info("[AUDIT] start " + action
                    + " timestamp=" + Instant.now()
                    + " args=" + Arrays.toString(pjp.getArgs()));
        } else {
            log.info("[AUDIT] start " + action
                    + " timestamp=" + Instant.now());
        }

        try {
            Object result = pjp.proceed();

            log.info("[AUDIT] " + action + " success");
            return result;

        } catch (Throwable ex) {
            log.severe("[AUDIT] " + action
                    + " failure: "
                    + ex.getClass().getSimpleName()
                    + ": "
                    + ex.getMessage());

            throw ex;
        }
    }
}