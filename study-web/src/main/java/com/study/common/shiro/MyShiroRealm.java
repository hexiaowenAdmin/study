package com.study.common.shiro;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.study.service.SysService;
import com.study.service.UserService;

/**
 * realm(登陆认证和权限认证)
 * @author 何小文
 *
 */
@Configuration
public class MyShiroRealm extends AuthorizingRealm{
	
	@Autowired(required = false)
	private UserService userService;
	
	@Autowired(required = false)
	private SysService sysService;
	
	/**
	 * 权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		int id = principal.getId();
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		//用户的权限集合
		Set<String> setSys = sysService.queryMentId(id);
		simpleAuthorInfo.addStringPermissions(setSys);
		return simpleAuthorInfo;
	}
	
	/**
	 * 登陆认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = token.getUsername();
		String pwd = String.valueOf(token.getPassword());
		Map<String, Object> user = userService.queryByuserName(username);
		if(user == null){
	           throw new UnknownAccountException();
		}else{
			String password = String.valueOf(user.get("pass_word"));
			int userId = (Integer)user.get("user_id");
			return  new SimpleAuthenticationInfo(new Principal(username, password, userId),pwd , getName());
		}
		
	}
	public static class Principal implements Serializable{
		private String userNmae;
		private String password ;
		private int id ;
		public Principal(String userNmae,String password,int id){
			this.password = password;
			this.id = id;
			this.userNmae = userNmae;
		}
		public String getUserNmae() {
			return userNmae;
		}
		public void setUserNmae(String userNmae) {
			this.userNmae = userNmae;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
	}

}

