package pers.ljy.background.service.authority.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.ljy.background.mapper.SysRoleMenuDao;
import pers.ljy.background.model.SysRoleMenuEntity;
import pers.ljy.background.service.authority.SysRoleMenuService;
import pers.ljy.background.share.dao.BaseDao;
import pers.ljy.background.share.service.impl.BaseServiceImpl;
import pers.ljy.background.web.vo.authority.RoleMenuVo;

@Service
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuEntity, Integer> implements SysRoleMenuService {

	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Override
	public BaseDao<SysRoleMenuEntity, Integer> getDao() {
		return sysRoleMenuDao;
	}
	
	@Override
	public CopyOnWriteArrayList<SysRoleMenuEntity> selectRoleMenuByRoleId(Integer roleId) {
		return this.sysRoleMenuDao.selectRoleMenuByRoleId(roleId);
	}

	@Override
	public CopyOnWriteArrayList<RoleMenuVo> selectRoleMenuByRoleIdIn(List<Integer> roleIds) {
		return this.sysRoleMenuDao.selectRoleMenuByRoleIdIn(roleIds);
	}


	

}
