package pers.ljy.background.mapper;



import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.ibatis.annotations.Param;

import pers.ljy.background.model.SysResourceMenusEntity;
import pers.ljy.background.share.dao.BaseDao;

/***
 * 文件名称: SysResourceMenusDao.java
 * 文件描述: 资源菜单dao接口
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年05月09日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface SysResourceMenusDao extends BaseDao<SysResourceMenusEntity, Integer> {
    
	/**
	 * 根据pid获取下级数据
	 * @param pid
	 * @return
	 */
	CopyOnWriteArrayList<SysResourceMenusEntity> selectByPid(String pid);
	
	/**
	 * 获取菜单和按钮 资源信息
	 * @param pid
	 * @return
	 */
	CopyOnWriteArrayList<SysResourceMenusEntity> selectByMenuTypeNotIn();
	
	/**
	 * 根据不同条件查询数据
	 * @param form
	 * @return
	 */
	CopyOnWriteArrayList<SysResourceMenusEntity> selectByForm(SysResourceMenusEntity form);
	
	/**
	 * 获取最大编号值
	 * @param pid
	 * @return
	 */
	String selectMaxPid(String pid);
	
	/**
	 * 根据PID 模糊匹配查询
	 * @param pid
	 * @return
	 */
	CopyOnWriteArrayList<SysResourceMenusEntity> selectByPidLike(String pid);
	
	/**
	 * 角色已分配资源列表
	 * @param roleId
	 * @param menuType
	 * @return
	 */
	CopyOnWriteArrayList<SysResourceMenusEntity> selectByRoleIdIn(@Param("roleId")Integer roleId,@Param("menuType")String menuType);
	
	/**
	 * 角色未分配资源列表
	 * @param roleId
	 * @return
	 */
	CopyOnWriteArrayList<SysResourceMenusEntity> selectByRoleIdNotIn(Integer roleId);
	
}