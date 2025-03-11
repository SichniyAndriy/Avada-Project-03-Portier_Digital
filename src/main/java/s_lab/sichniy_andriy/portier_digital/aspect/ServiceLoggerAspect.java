package s_lab.sichniy_andriy.portier_digital.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component @Aspect
public class ServiceLoggerAspect {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public ServiceLoggerAspect() {
        LOGGER.debug("ServiceLoggerAspect created");
    }


    @Before("within(s_lab.sichniy_andriy.portier_digital.service..*)")
    public void serviceLoggerBefore(JoinPoint joinPoint) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Method: {} on class: {} invoked",
                    joinPoint.getSignature().getName(),
                    joinPoint.getTarget().getClass().getSimpleName());
        }
    }

    @After("within(s_lab.sichniy_andriy.portier_digital.service..*)")
    public void serviceLoggerAfter(JoinPoint joinPoint) {
        if (LOGGER.isDebugEnabled()) {
            if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Method: {} on class: {} finished",
                    joinPoint.getSignature().getName(),
                    joinPoint.getTarget().getClass().getSimpleName());
            }
        }
    }
}
