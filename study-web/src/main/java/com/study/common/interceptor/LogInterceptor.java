package com.study.common.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.study.entity.LogAnnotation;
import com.study.utils.IpUtil;

/**
 * 自定义日志拦截器
 * @author 何小文
 */
@Aspect  
@Component
public class LogInterceptor {

	@Autowired  
	private HttpServletRequest request;
	
	
	/**配置切面,切到service层*/
	@Pointcut("execution(* com.study.controller.*.*(..))")  
    public void log(){}  
  
	/**
     * 返回之前通知
     * @param ret
     * @throws Throwable
     */
	@AfterReturning(returning = "ret",pointcut="log()")  
    public void doAfterReturning(JoinPoint joinPoint) throws Throwable {  
    	String targetName = joinPoint.getTarget().getClass().getName();
        Class targetClass = null;
		try {
			targetClass = Class.forName(targetName);
			String methodName = joinPoint.getSignature().getName();
	        Method[] method = targetClass.getMethods();
	        Object[] params = joinPoint.getArgs(); 
	        for (Method m : method) {
	            if (m.getName().equals(methodName)) {
	                Class[] tmpCs = m.getParameterTypes();
	                if (tmpCs.length == params.length) {
	                    /**拦截定义了@LogAnnotation注解的方法*/
	                    LogAnnotation logAnnotation = m.getAnnotation(LogAnnotation.class);
	                    if(logAnnotation != null){
	                    	Map<String, Object> param = new HashMap<String, Object>();
	                    	param.put("content", logAnnotation.content());
	                    	param.put("model", logAnnotation.moduleName());
	                    	param.put("ip", IpUtil.getIpAddress(request));
	                    	System.out.println(logAnnotation.moduleName()+"!!!!!!!!!!!!!!!");
	                    	/**插入日志*/
	                    }
	                }
	            }
	        }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    } 
	
	/**
	 * 方法执行之前
	 * @param joinPoint
	 * @throws Throwable
	 */
    @Before("log()")  
    public void deBefore(JoinPoint joinPoint) throws Throwable {  
    	
    }  
    
    /**
     * 后置异常通知  
     * @param jp
     */
    @AfterThrowing("log()")  
    public void throwss(JoinPoint jp){  
        
    }  
  
    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行  
     * @param jp
     */
    @After("log()")  
    public void after(JoinPoint jp){  
          
    }  
  
    /**
     * 环绕通知,放行后必须返回处理结果,即return处理结果
     * @param pjp
     * @return Object
     */
    @Around("log()")  
    public Object arround(ProceedingJoinPoint pjp) {  
        try{
        	/**放行并返回处理结果*/
            Object resul =  pjp.proceed();  
            return resul;  
        }catch(Throwable e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
}
