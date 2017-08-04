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
}
