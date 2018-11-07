package com.study.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.common.shiro.MyShiroRealm.Principal;


/**
 * 登陆处理controller
 * @author 何小文
 *
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * 登陆拦截
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request){
        String getContextPath = request.getContextPath();  
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+getContextPath+"/login";  
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("basePath",basePath);
		modelAndView.setViewName("user/login");
		return modelAndView;
	}
	
	/**
	 * 验证登陆
	 * @param username 用户名
	 * @param password 密码
	 * @param rememberMe 是否记住密码
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String username, String password,Boolean rememberMe, 
			HttpServletRequest req) {
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password, rememberMe);
		token.setRememberMe(rememberMe);
		Subject currentUser = SecurityUtils.getSubject();
		try {
			// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			logger.info("对用户[" + username + "]进行登录验证..验证开始");
			currentUser.login(token);
			logger.info("对用户[" + username + "]进行登录验证..验证通过");
		} catch (UnknownAccountException uae) {
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
		} catch (IncorrectCredentialsException ice) {
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
		} catch (LockedAccountException lae) {
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
		} catch (ExcessiveAttemptsException eae) {
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
		} catch (AuthenticationException ae) {
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
			ae.printStackTrace();
		}
		if (currentUser.isAuthenticated()){
			return "redirect:/index";
		}else {
			token.clear();
			return "redirect:/logout";
		}

	}
	
	/**
	 * 主页
	 * @return
	 */
    @RequestMapping(value="/index",method=RequestMethod.GET)
    public ModelAndView home(){
    	ModelAndView modelAndView = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        @SuppressWarnings("all")
		Principal principal = (Principal) subject.getPrincipal();
        modelAndView.setViewName("user/success");
        return modelAndView;
    }
    /**
     * 退出登陆
     * @return
     */
    @RequestMapping(value="/logout",method=RequestMethod.GET)  
    public String logout(RedirectAttributes redirectAttributes) {
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }
}
