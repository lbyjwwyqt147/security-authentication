package pers.ljy.background.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import pers.ljy.background.datasource.DataSourceAop;
import pers.ljy.background.model.SysRoleEntity;
import pers.ljy.background.model.SysRoleMenuEntity;
import pers.ljy.background.model.SysUserRoleEntity;
import pers.ljy.background.model.SysUsersAccountEntity;
import pers.ljy.background.service.authority.SysRoleMenuService;
import pers.ljy.background.service.user.SysUsersAccountService;
import pers.ljy.background.web.vo.authority.UserRoleVo;

/***
 * 文件名称: CustomUserDetailsService.java  用户请求登录会进入此类的loadUserByUsername方法 验证用户
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
	public static final Logger logger = LoggerFactory.getLogger(DataSourceAop.class);
	@Autowired
	private SysUsersAccountService usersAccountService;
	@Autowired
    private SysRoleMenuService roleMenuService;	
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		logger.info(" userName: " + userName);
		//获取账户信息
		UserRoleVo usersRoleVo = this.usersAccountService.selectUsersAccountRoles(userName);
		if(usersRoleVo == null){
			throw new UsernameNotFoundException("UserName " + userName + " not found"); 
		}
		return null;
	}
	
	/**
	 * 获取用户的权限
	 * @param usersRoleVo
	 * @return
	 */
    private Set<GrantedAuthority> obtionGrantedAuthorities(UserRoleVo usersRoleVo) {
        CopyOnWriteArrayList<SysRoleMenuEntity> resources = new CopyOnWriteArrayList<>();
        //获取用户的角色
        Set<SysUserRoleEntity> roles = usersRoleVo.getUserRoleList();
        List<Integer> roleIds =  new ArrayList<>();
        for (SysUserRoleEntity role : roles) {
        	roleIds.add(role.getRoleId());
        }
        //根据角色ID获取角色拥有的菜单资源
        Set<WebResource> res = role.getResources();
        for (WebResource resource : res) {
            resources.add(resource);
        }
        
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        for (WebResource r : resources) {
            //用户可以访问的资源名称（或者说用户所拥有的权限）
            authSet.add(new SimpleGrantedAuthority(r.getValue()));
        }
        return authSet;
    }
	
	

}
