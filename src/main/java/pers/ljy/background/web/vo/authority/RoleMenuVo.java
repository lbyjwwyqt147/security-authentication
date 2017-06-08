package pers.ljy.background.web.vo.authority;

import java.util.Set;

import pers.ljy.background.model.SysResourceMenusEntity;
import pers.ljy.background.web.vo.BasicVo;

/***
 * 角色拥有的菜单资源vo
 * @author ljy
 *
 */
public class RoleMenuVo extends BasicVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
   /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 菜单ID
     */
    private Integer menuId;
    
    /**
     * 角色菜单资源
     */
    private Set<SysResourceMenusEntity> resourceMenusList;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Set<SysResourceMenusEntity> getResourceMenusList() {
		return resourceMenusList;
	}

	public void setResourceMenusList(Set<SysResourceMenusEntity> resourceMenusList) {
		this.resourceMenusList = resourceMenusList;
	}

    

}
