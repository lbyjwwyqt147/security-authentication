package pers.ljy.background.mapper;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import pers.ljy.background.model.SysRoleEntity;
import pers.ljy.background.share.dao.BaseDao;

/***
 * 文件名称: SysRoleDao.java
 * 文件描述: 角色dao接口
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年05月09日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface SysRoleDao extends BaseDao<SysRoleEntity, Integer> {
    
    /**
     * 根据一组ID 获取角色列表
     * @param ids
     * @return
     */
	CopyOnWriteArrayList<SysRoleEntity> selectByPrimaryKeyIn(List<Integer> ids);
	

	/**
	 * 根据用户ID 获取还未分配的角色
	 * @param userId
	 * @return
	 */
	CopyOnWriteArrayList<SysRoleEntity> selectUserRoleByUserIdNotIn(Integer userId);
	

	/**
	 * 根据用户ID 获取已分配的角色
	 * @param userId
	 * @return
	 */
	CopyOnWriteArrayList<SysRoleEntity> selectUserRoleByUserIdIn(Integer userId);
}