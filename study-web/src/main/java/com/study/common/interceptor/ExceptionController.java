package com.study.common.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
/**
 * 异常处理类
 * @author 何小文
 *
 */
@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(value = UnauthorizedException.class)//处理访问方法时权限不足问题
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e)  {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user/403");
		return modelAndView;
	}
}
