package com.alasdoo.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;

/**
 * Use this aspect as a lightweight system performance profiler.
 * @author Turzai
 *
 */
@Aspect
public class ProfilingAspect {

//	@Around("methodsToBeProfiled()")
//    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
//		System.out.println("Start aspect");
//        StopWatch sw = new StopWatch(getClass().getSimpleName());
//        try {
//            sw.start(pjp.getSignature().getName());
//            return pjp.proceed();
//        } finally {
//            sw.stop();
//            System.out.println(sw.prettyPrint());
//        }
//    }
//
//    @Pointcut("execution(* com.alasdoo.repositories.*.*(..))")
//    public void methodsToBeProfiled(){}
//    
}
