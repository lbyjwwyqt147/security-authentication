package pers.ljy.background.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import pers.ljy.background.model.SysUsersAccountEntity;
import pers.ljy.background.share.dao.BaseDao;
import pers.ljy.background.web.vo.authority.UserRoleVo;

/***
 * 文件名称: SysUsersAccountDao.java
 * 文件描述: 账户dao接口
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年05月09日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Mapper
public interface SysUsersAccountDao extends BaseDao<SysUsersAccountEntity, Integer>{
    
	/**
	 * 根据用户名查询账户信息
	 * @param userName  用户名
	 * @return
	 */
	SysUsersAccountEntity selectUsersAccount(String userName);
	
	/**
	 * 根据用户名,密码 查询账户信息
	 * @param userName  用户名
	 * @param pwd       密码
	 * @return
	 */
	SysUsersAccountEntity selectUsersAccountByUserNameAndByUserPwd(@Param("userName")String userName,@Param("userPwd")String userPwd);
	
	/**
	 * 根据用户名查询账户信息和对于的角色
	 * @param userName  用户名
	 * @return
	 */
	UserRoleVo selectUsersAccountRoles(String userName);
}