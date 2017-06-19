package pers.ljy.background.service.user;

import java.util.concurrent.atomic.AtomicBoolean;

import pers.ljy.background.model.SysUsersAccountEntity;
import pers.ljy.background.share.service.BaseService;
import pers.ljy.background.web.vo.authority.UserRoleVo;
import pers.ljy.background.web.vo.user.UsersAccountVo;

/***
 * 文件名称: SysUsersAccountService.java
 * 文件描述: 账户service接口
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年05月15日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface SysUsersAccountService extends BaseService<SysUsersAccountEntity, Integer>{

	/**
	 * 根据用户名查询账户信息
	 * @param userName  用户名
	 * @return
	 */
	SysUsersAccountEntity selectUsersAccount(String userName);
	
	/**
	 * 根据用户名查询账户信息和对于的角色
	 * @param userName  用户名
	 * @return
	 */
	UserRoleVo selectUsersAccountRoles(String userName);
	
	/**
	 * 用户注册
	 * @param usersAccountVo
	 * @return
	 */
	AtomicBoolean registeredSave(UsersAccountVo usersAccountVo);
}
