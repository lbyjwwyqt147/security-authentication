package pers.ljy.background.service.authority;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import pers.ljy.background.model.SysRoleEntity;

/***
 * 文件名称: SysRoleService.java
 * 文件描述: 角色service接口
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年05月09日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
public interface SysRoleService {

	/**
     * 根据一组ID 获取角色列表
     * @param ids
     * @return
     */
	CopyOnWriteArrayList<SysRoleEntity> selectByPrimaryKeyIn(List<Integer> ids);
    /**
     * 一组角色名称
     * @param ids
     * @return
     */
    ConcurrentMap<String, String> roleBatchMap(List<Integer> ids);
}
