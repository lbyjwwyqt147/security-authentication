package pers.ljy.background.mapper;



import org.apache.ibatis.annotations.Mapper;

import pers.ljy.background.model.UserInfoEntity;
import pers.ljy.background.share.dao.BaseDao;

/***
 * 文件名称: UserInfoDao.java
 * 文件描述: 用户信息 dao接口
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年06月19日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Mapper
public interface UserInfoDao extends BaseDao<UserInfoEntity, Integer> {
    
}