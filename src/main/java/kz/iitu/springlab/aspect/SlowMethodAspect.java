package kz.iitu.springlab.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

@Aspect
@Component
public class SlowMethodAspect {

    private static final Logger log =
            Logger.getLogger(SlowMethodAspect.class.getName());

    private final long thresholdMs;

    private final List<String> slowCalls = new CopyOnWriteArrayList<>();

    public SlowMethodAspect(
            @Value("${app.aspect.slow-threshold-ms}") long thresholdMs) {
        this.thresholdMs = thresholdMs;
    }
    @Around("kz.iitu.springlab.aspect.Pointcuts.serviceOperation()")
    public Object trackSlowMethod(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();

        try {
            return pjp.proceed();
        } finally {
            long durationMs = (System.nanoTime() - start) / 1_000_000;

            if (durationMs > thresholdMs) {
                String record = pjp.getSignature().toShortString()
                        + " — " + durationMs + " ms";

                slowCalls.add(record);
                log.warning("[SLOW-METHOD] " + record);
            }
        }
    }
    public List<String> getSlowCalls() {
        return List.copyOf(slowCalls);
    }
}