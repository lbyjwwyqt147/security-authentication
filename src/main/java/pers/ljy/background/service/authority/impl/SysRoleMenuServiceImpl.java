package pers.ljy.background.service.authority.impl;

import java.util.Date;
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

	@Override
	public void batchInset(Integer roleId, CopyOnWriteArrayList<Integer> menusIds) {
		CopyOnWriteArrayList<SysRoleMenuEntity> list = new CopyOnWriteArrayList<>();
		menusIds.forEach(item -> {
			SysRoleMenuEntity entity = new SysRoleMenuEntity();
			entity.setCreateDate(new Date());
			entity.setCreateUserId(1);
			entity.setMenuId(item);
			entity.setRoleId(roleId);
			list.add(entity);
		});
		this.sysRoleMenuDao.batchInset(list);
	}

	@Override
	public void batchDelete(Integer roleId, CopyOnWriteArrayList<Integer> menusIds) {
		this.sysRoleMenuDao.batchDelete(roleId, menusIds);
	}


	

}
