package kz.iitu.springlab.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
@Order(3)
public class TimingAspect {

    private static final Logger log =
            Logger.getLogger(TimingAspect.class.getName());

    @Around("kz.iitu.springlab.aspect.Pointcuts.serviceOperation()")
    public Object measure(ProceedingJoinPoint pjp) throws Throwable {
        long started = System.nanoTime();

        try {
            return pjp.proceed();
        } finally {
            long ms = (System.nanoTime() - started) / 1_000_000;
            String name = pjp.getSignature().toShortString();

            if (ms > 200) {
                log.warning("[TIME] SLOW: " + name + " — " + ms + " ms");
            } else {
                log.info("[TIME] " + name + " — " + ms + " ms");
            }
        }
    }
}