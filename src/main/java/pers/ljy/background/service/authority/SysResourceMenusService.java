package pers.ljy.background.service.authority;

import java.util.concurrent.CopyOnWriteArrayList;

import pers.ljy.background.model.SysResourceMenusEntity;
import pers.ljy.background.share.component.tree.JsTree;
import pers.ljy.background.share.service.BaseService;

/***
 * 文件名称: SysResourceMenusService.java
 * 文件描述: 资源菜单service接口
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年06月12日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface SysResourceMenusService  extends BaseService<SysResourceMenusEntity, Integer>  {

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
	 * 获取最大值
	 * @param pid
	 * @return
	 */
	String selectMaxPid(String pid);
	
	/**
	 * 得到最新编号值
	 * @param pid
	 * @return
	 */
	String newestNumber(String pid);
	
	/**
	 * 资源菜单树
	 * @param pid
	 * @return
	 */
    JsTree menusTree(String pid);
    
    /**
     * 角色分配的资源
     * @param roleId
     * @return
     */
    JsTree roleMenusIn(Integer roleId);
    
    /**
     * 角色未分配的资源
     * @param roleId
     * @return
     */
    JsTree roleMenusNotIn(Integer roleId);
    
    /**
     * 获取用户拥有的资源菜单
     * @param userId
     * @param token
     * @return
     */
    CopyOnWriteArrayList<JsTree> userMenus(String userId,String token);
    
    /**
	 * 根据PID 模糊匹配查询
	 * @param pid
	 * @return
	 */
	CopyOnWriteArrayList<SysResourceMenusEntity> selectByPidLike(String pid);
	
	/**
	 * 角色已分配资源列表
	 * @param roleId
	 * @return
	 */
	CopyOnWriteArrayList<SysResourceMenusEntity> selectByRoleIdIn(Integer roleId,String menuType);
	
	/**
	 * 角色未分配资源列表
	 * @param roleId
	 * @return
	 */
	CopyOnWriteArrayList<SysResourceMenusEntity> selectByRoleIdNotIn(Integer roleId);
	
    
    
}
