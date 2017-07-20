package pers.ljy.background.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pers.ljy.background.model.SysResourceMenusEntity;
import pers.ljy.background.model.SysRoleEntity;
import pers.ljy.background.model.SysUserRoleEntity;
import pers.ljy.background.model.SysUsersAccountEntity;
import pers.ljy.background.service.authority.SysRoleMenuService;
import pers.ljy.background.service.authority.SysRoleService;
import pers.ljy.background.service.authority.SysUserRoleService;
import pers.ljy.background.service.user.SysUsersAccountService;
import pers.ljy.background.web.vo.authority.RoleMenuVo;

/***
 * 文件名称: MyUserDetailService.java  用户请求登录会进入此类的loadUserByUsername方法 验证用户
 * 文件描述: 权限验证类
 * User userdetail该类实现 UserDetails 接口，该类在验证成功后会被保存在当前回话的principal对象中
 * 
 * 获得对象的方式：
 * WebUserDetails webUserDetails = (WebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * 
 * 或在JSP中：
 * <sec:authentication property="principal.username"/>
 * 
 * 如果需要包括用户的其他属性，可以实现 UserDetails 接口中增加相应属性即可
 * 
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年05月15日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Component
public class MyUserDetailService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailService.class);
	@Autowired
	private SysUsersAccountService usersAccountService;
	@Autowired
    private SysRoleMenuService roleMenuService;	
	@Autowired
	private SysRoleService roleService;
	@Autowired
	private SysUserRoleService userRoleService;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		LOGGER.info(" userName: " + userName);
		//获取账户信息
		SysUsersAccountEntity users = this.usersAccountService.selectUsersAccount(userName);
		if(users == null){
			throw new UsernameNotFoundException("UserName " + userName + " not found"); 
		}
		
		// 取得用户的权限
		CopyOnWriteArrayList<SysUserRoleEntity> userRoleList = this.userRoleService.selectUserRoleByUserId(users.getId());
        Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(users,userRoleList);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        List<Integer> roleIdsList = new ArrayList<>();
        for (SysUserRoleEntity role : userRoleList) {
        	roleIdsList.add(role.getRoleId());
        }
        CopyOnWriteArrayList<SysRoleEntity> roleEntitieList = this.roleService.selectByPrimaryKeyIn(roleIdsList);
        for (SysRoleEntity sysRoleEntity : roleEntitieList) {
            grantedAuthorities.add(new SimpleGrantedAuthority(sysRoleEntity.getRoleName()));
		}
        // 封装成spring security的user
        User userDetail = new User(users.getUserName(), users.getUserPwd(),
                true,//是否可用
                true,//是否过期
                true,//证书不过期为true
                true,//账户未锁定为true ,
                grantedAuths);
        return userDetail;
	}
	
	/**
	 * 获取用户的权限
	 * @param usersRoleVo
	 * @return
	 */
    private Set<GrantedAuthority> obtionGrantedAuthorities(SysUsersAccountEntity users,CopyOnWriteArrayList<SysUserRoleEntity> userRoleList ) {
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        //获取用户的角色
        CopyOnWriteArraySet<SysUserRoleEntity> roles = new CopyOnWriteArraySet<>(userRoleList);
        CopyOnWriteArrayList<Integer> roleIds =  new CopyOnWriteArrayList<>();
        for (SysUserRoleEntity role : roles) {
        	roleIds.add(role.getRoleId());
        }
        //根据角色ID获取角色拥有的菜单资源
        CopyOnWriteArrayList<RoleMenuVo> roleMenuList = this.roleMenuService.selectRoleMenuByRoleIdIn(roleIds);
        for (RoleMenuVo roleMenuVo : roleMenuList) {
            Set<SysResourceMenusEntity> res = roleMenuVo.getResourceMenusList();
            for (SysResourceMenusEntity sysResourceMenusEntity : res) {
            	//用户可以访问的资源名称（或者说用户所拥有的权限标识）
                authSet.add(new SimpleGrantedAuthority(sysResourceMenusEntity.getAuthorizedSigns()));
			}
        }
        return authSet;
    }
	
	

}
