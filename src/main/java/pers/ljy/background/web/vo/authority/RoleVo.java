package pers.ljy.background.web.vo.authority;

import java.util.Date;

import pers.ljy.background.web.vo.BasicVo;

public class RoleVo extends BasicVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	  /**
     * 角色标识
     */
    private String roleMarking;
    
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 状态 1001：禁用   1002：正常
     */
    private String status;

    /**
     * 创建人
     */
    private Date createDate;

    /**
     * 创建时间
     */
    private Integer createUserId;

	public String getRoleMarking() {
		return roleMarking;
	}

	public void setRoleMarking(String roleMarking) {
		this.roleMarking = roleMarking;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
    
    

}
