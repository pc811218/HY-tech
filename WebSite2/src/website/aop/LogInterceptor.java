package website.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class LogInterceptor {
    // 宣告並取得logger
    private Logger logger = Logger.getLogger(LogInterceptor.class);
    
    // 程式在進入website下的方法前皆會執行此段程式
    @Around("execution(* website.model..*(..))")
    public Object invoke(ProceedingJoinPoint jointPoint) throws Throwable {
        // 將訊息寫至logger
        logger.info("進來了!!!");
        //找出目前執行方法
        logger.info("目前執行方法(前):"+jointPoint.getSignature().getName());
        
        Object retVal = jointPoint.proceed();
        
        logger.info("目前執行方法(後):"+jointPoint.getSignature().getName());
        
        return retVal;
    }
    
    //發生Exception時會紀錄
    @AfterThrowing(pointcut="execution(* website..*(..))", throwing="throwable")
    public void afterThrowingLogAdvice(JoinPoint jointPoint, Throwable throwable) { 
        logger.info("發生Exception! 發生的程式為:" 
        		   +jointPoint.getSignature().getDeclaringTypeName() 
                   + "." 
                   + jointPoint.getSignature().getName()
                   + " Exception的型態為:"+throwable);
    } 
}
