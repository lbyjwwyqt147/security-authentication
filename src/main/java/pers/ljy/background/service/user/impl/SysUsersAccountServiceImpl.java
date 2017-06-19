package pers.ljy.background.service.user.impl;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.ljy.background.mapper.SysUsersAccountDao;
import pers.ljy.background.model.SysUsersAccountEntity;
import pers.ljy.background.model.UserInfoEntity;
import pers.ljy.background.service.user.SysUsersAccountService;
import pers.ljy.background.service.user.UserInfoService;
import pers.ljy.background.share.dao.BaseDao;
import pers.ljy.background.share.service.impl.BaseServiceImpl;
import pers.ljy.background.share.utils.DozerMapper;
import pers.ljy.background.web.vo.authority.UserRoleVo;
import pers.ljy.background.web.vo.user.UsersAccountVo;

/***
 * 文件名称: SysUsersAccountServiceImpl.java
 * 文件描述: 账户service接口 实现
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年05月15日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Transactional
@Service
public class SysUsersAccountServiceImpl extends BaseServiceImpl<SysUsersAccountEntity, Integer> implements SysUsersAccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUsersAccountServiceImpl.class);
	@Autowired
	private SysUsersAccountDao sysUsersAccountDao;
	@Autowired
	private UserInfoService userInfoService;

	@Override
	public BaseDao<SysUsersAccountEntity, Integer> getDao() {
		return sysUsersAccountDao;
	}
	
	
	@Override
	public SysUsersAccountEntity selectUsersAccount(String userName) {
		return this.sysUsersAccountDao.selectUsersAccount(userName);
	}


	@Override
	public UserRoleVo selectUsersAccountRoles(String userName) {
		return this.selectUsersAccountRoles(userName);
	}


	@Override
	public AtomicBoolean registeredSave(UsersAccountVo usersAccountVo) {
		AtomicBoolean success = new AtomicBoolean(false);
		if(StringUtils.isBlank(usersAccountVo.getUserName())){
            throw new RuntimeException("帐号不能为空.");
		}
		if(StringUtils.isBlank(usersAccountVo.getUserPwd())){
            throw new RuntimeException("密码不能为空.");
		}
        try {
    		//用户信息
    		UserInfoEntity userInfo = DozerMapper.map(usersAccountVo, UserInfoEntity.class);
    		String userNumber = String.valueOf(System.nanoTime());
    		userInfo.setUserNumber(userNumber);
    		int userId = this.userInfoService.insert(userInfo);
    		//帐号信息
    		SysUsersAccountEntity  account = DozerMapper.map(usersAccountVo, SysUsersAccountEntity.class);
    		account.setUserId(userId);
    		account.setUserNumber(userNumber);
    		int count = this.sysUsersAccountDao.insert(account);
    		if(count > 0){
    			success.set(true);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("用户注册出现异常.");
		}

		return success;
	}

	

	
}
