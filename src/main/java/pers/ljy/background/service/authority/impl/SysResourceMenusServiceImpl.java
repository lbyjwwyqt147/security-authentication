package pers.ljy.background.service.authority.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.ljy.background.mapper.SysResourceMenusDao;
import pers.ljy.background.model.SysResourceMenusEntity;
import pers.ljy.background.service.authority.SysResourceMenusService;
import pers.ljy.background.share.dao.BaseDao;
import pers.ljy.background.share.service.impl.BaseServiceImpl;

/***
 * 文件名称: SysResourceMenusServiceImpl.java
 * 文件描述: 资源菜单service接口 实现
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年06月12日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Service
public class SysResourceMenusServiceImpl extends BaseServiceImpl<SysResourceMenusEntity, Integer> implements SysResourceMenusService {

	@Autowired
	private SysResourceMenusDao sysResourceMenusDao;
	
	@Override
	public BaseDao<SysResourceMenusEntity, Integer> getDao() {
		return sysResourceMenusDao;
	}

	
}
