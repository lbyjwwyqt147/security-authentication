package pers.ljy.background.service.user.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.ljy.background.mapper.UserInfoDao;
import pers.ljy.background.model.UserInfoEntity;
import pers.ljy.background.service.user.UserInfoService;
import pers.ljy.background.share.dao.BaseDao;
import pers.ljy.background.share.service.impl.BaseServiceImpl;

/***
 * 文件名称: UserInfoServiceImpl.java
 * 文件描述: 用户信息 service接口 实现
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年06月19日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Transactional
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoEntity, Integer> implements UserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;
	
	@Override
	public BaseDao<UserInfoEntity, Integer> getDao() {
		return userInfoDao;
	}


}
