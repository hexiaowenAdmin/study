package com.study.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.entity.LogAnnotation;
import com.study.service.UserService;
import com.study.utils.WebJson;

/**
 * 用户controller
 * @author 何小文
 *
 */

@Controller
@RequestMapping("/userController")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 获取所有用户
	 * @param request
	 * @param response
	 */
	@ApiOperation(value="获取用户列表", notes="")
	@LogAnnotation(moduleName="用户",content="查询所有")
	@RequestMapping("getUser")
	public void getUser(HttpServletRequest request, HttpServletResponse response){
		PageHelper.startPage(1,1);//第几页,每页行数
		List<Map<String, Object>> list = userService.queryUesr();
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list);
		WebJson.writeJson(pageInfo, response);
	}
	
	@ApiOperation(value="获取用户信息", notes="通过用户ID获取用户信息")
	@RequestMapping("getUserById")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String"),
		@ApiImplicitParam(name = "name", value = "用户名称", required = false, dataType = "String")
	})
	public void getUserById(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "张三");
		map.put("age", 18);
		WebJson.writeJson(map, response);
	}
	
	@RequestMapping("/test")
	@RequiresPermissions("10")
	public ModelAndView test(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user/a");
		return modelAndView;
	}
}
