package pers.ljy.background.web.controller.user;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pers.ljy.background.service.user.SysUsersAccountService;
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
}
