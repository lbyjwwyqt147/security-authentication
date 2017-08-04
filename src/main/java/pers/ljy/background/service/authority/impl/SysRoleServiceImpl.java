package pers.ljy.background.service.authority.impl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.ljy.background.mapper.SysRoleDao;
import pers.ljy.background.model.SysRoleEntity;
import pers.ljy.background.service.authority.SysRoleService;
import pers.ljy.background.share.dao.BaseDao;
import pers.ljy.background.share.service.impl.BaseServiceImpl;
/***
 * 文件名称: SysRoleServiceImpl.java
 * 文件描述: 角色service接口实现
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年05月09日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleEntity,Integer> implements SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;
	
	@Override
	public BaseDao<SysRoleEntity, Integer> getDao() {
		return sysRoleDao;
	}
	
	@Override
	public CopyOnWriteArrayList<SysRoleEntity> selectByPrimaryKeyIn(List<Integer> ids) {
		CopyOnWriteArrayList<SysRoleEntity> list =  this.sysRoleDao.selectByPrimaryKeyIn(ids);
		if(list == null || list.isEmpty()){
			list = new CopyOnWriteArrayList<>();
		}
		return list;
	}

	@Override
	public ConcurrentMap<String, String> roleBatchMap(List<Integer> ids) {
		CopyOnWriteArrayList<SysRoleEntity> list = this.sysRoleDao.selectByPrimaryKeyIn(ids);
		ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
		for (SysRoleEntity sysRoleEntity : list) {
			map.put(sysRoleEntity.getId().toString(), sysRoleEntity.getRoleName());
		}
		return map;
	}

	@Override
	public CopyOnWriteArrayList<SysRoleEntity> selectUserRoleByUserIdNotIn(Integer userId) {
		return this.sysRoleDao.selectUserRoleByUserIdNotIn(userId);
	}

	@Override
	public CopyOnWriteArrayList<SysRoleEntity> selectUserRoleByUserIdIn(Integer userId) {
		return this.sysRoleDao.selectUserRoleByUserIdIn(userId);
	}

	

}
