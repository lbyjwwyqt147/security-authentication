package pers.ljy.background.mapper;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import pers.ljy.background.model.SysRoleMenuEntity;
import pers.ljy.background.share.dao.BaseDao;
import pers.ljy.background.web.vo.authority.RoleMenuVo;

/***
 * 文件名称: SysRoleMenuDao.java
 * 文件描述: 角色菜单资源dao接口
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年05月09日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Mapper
public interface SysRoleMenuDao extends BaseDao<SysRoleMenuEntity, Integer>{
     
	/**
	 * 根据角色ID获取角色菜单资源
	 * @param roleId 角色ID
	 * @return
	 */
	CopyOnWriteArrayList<SysRoleMenuEntity> selectRoleMenuByRoleId(Integer roleId);
	
	/**
	 * 根据一组角色ID获取角色菜单资源
	 * @param roleIds 角色ID
	 * @return
	 */
	CopyOnWriteArrayList<RoleMenuVo> selectRoleMenuByRoleIdIn(List<Integer> roleIds);
	
	/**
	 * 批量添加
	 * @param entityList
	 */
	void batchInset(CopyOnWriteArrayList<SysRoleMenuEntity> entityList);
	
	/**
	 * 批量删除
	 * @param roleId
	 * @param menusIds
	 */
	void batchDelete(@Param("roleId")Integer roleId,@Param("menusIds")CopyOnWriteArrayList<Integer> menusIds);
}