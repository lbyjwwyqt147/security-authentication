package pers.ljy.background.web.controller.user;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pers.ljy.background.model.SysUsersAccountEntity;
import pers.ljy.background.service.user.SysUsersAccountService;
import pers.ljy.background.share.dto.PageForm;
import pers.ljy.background.share.exception.BusinessException;
import pers.ljy.background.share.logs.SystemControllerLog;
import pers.ljy.background.share.result.ApiResultCode;
import pers.ljy.background.share.result.ApiResultView;
import pers.ljy.background.web.controller.BasicController;
import pers.ljy.background.web.vo.user.UsersAccountVo;

/***
 * 文件名称: UserAccountController.java
 * 文件描述: 用户 controller
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年05月09日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@RestController
public class UserAccountController extends BasicController {
	@Autowired
    private SysUsersAccountService usersAccountService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/**
	 * 用户注册
	 * @param usersAccountVo
	 * @return
	 */
	@PostMapping("/users/signins")
	@SystemControllerLog(description = "用户注册") 
	public ApiResultView registered(UsersAccountVo usersAccountVo){
		int status = ApiResultCode.FAIL.getCode();
		String msg = "注册失败.";
		AtomicBoolean success = this.usersAccountService.registeredSave(usersAccountVo);
		if(success.get()){
			status = ApiResultCode.SUCCESS.getCode();
			msg = "注册成功.";
		}
		return this.buildRestful(status, msg, null);
	}
	
	/**
	 * 用户登录
	 * @param usersAccountVo
	 * @return
	 */
	@RequestMapping(value="/users/logins")
	@SystemControllerLog(description = "用户登录") 
	public ApiResultView logins(String userName,String userPwd,HttpServletRequest request,HttpServletResponse response){
		int status = ApiResultCode.FAIL.getCode();
		String msg = "登录失败.";
     
		SysUsersAccountEntity accountEntity  = this.usersAccountService.selectUsersAccount(userName);
		if(accountEntity != null){
			if (!passwordEncoder.matches(userPwd, accountEntity.getUserPwd())) {
				 throw new BusinessException("未知的用户名或者密码!");
	        }
			
            //身份认证
			// 这句代码会自动执行咱们自定义的 "MyUserDetailService.java" 类
			//1: 将用户名和密码封装成token  new UsernamePasswordAuthenticationToken(userName, userPwd)
			//2: 将token传给AuthenticationManager进行身份认证   authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPwd));
			//3: 认证完毕，返回一个认证后的身份： Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPwd));
			//4: 认证后，存储到SecurityContext里   SecurityContext securityContext = SecurityContextHolder.getContext();securityContext.setAuthentication(authentication);
			
			//UsernamePasswordAuthenticationToken继承AbstractAuthenticationToken实现Authentication
			//当在页面中输入用户名和密码之后首先会进入到UsernamePasswordAuthenticationToken验证(Authentication)，注意用户名和登录名都是页面传来的值
			//然后生成的Authentication会被交由AuthenticationManager来进行管理
			//而AuthenticationManager管理一系列的AuthenticationProvider，
			//而每一个Provider都会通UserDetailsService和UserDetail来返回一个
			//以UsernamePasswordAuthenticationToken实现的带用户名和密码以及权限的Authentication
	        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPwd));
	        if (!authentication.isAuthenticated()) {
	            throw new BusinessException("未知的用户名或者密码!");
	        }
	        //将身份 存储到SecurityContext里
	        SecurityContext securityContext = SecurityContextHolder.getContext();
	        securityContext.setAuthentication(authentication);
	        
            request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            request.getSession().setAttribute("SESSION_OPERATOR", accountEntity);
				    	        
			status = ApiResultCode.SUCCESS.getCode();
			msg = "登录成功.";

	   }else {
		   throw new BusinessException("未知的用户名或者密码!");
	   }

	   return this.buildRestful(status, msg, null);
	}
	
	/**
	 * 用户列表
	 * @return
	 */
	@GetMapping(value="/users")
	@SystemControllerLog(description="用户列表")
	public String userLists(){
		CopyOnWriteArrayList<SysUsersAccountEntity> list = this.usersAccountService.selectAll();
		PageForm<SysUsersAccountEntity> pageForm = new PageForm<SysUsersAccountEntity>();
		pageForm.setTotal(10);
		pageForm.setRows(list);
		return this.buildListsData(pageForm);
	}
}
