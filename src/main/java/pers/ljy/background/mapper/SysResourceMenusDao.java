package pers.ljy.background.mapper;



import java.util.concurrent.CopyOnWriteArrayList;

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
	 * 获取最大编号值
	 * @param pid
	 * @return
	 */
	String selectMaxPid(String pid);
}