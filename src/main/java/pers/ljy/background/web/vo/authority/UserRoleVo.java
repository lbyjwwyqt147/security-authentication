package pers.ljy.background.web.vo.authority;

import java.util.Date;
import java.util.List;
import java.util.Set;

import pers.ljy.background.model.SysUserRoleEntity;
import pers.ljy.background.web.vo.BasicVo;

/***
 * 用户对应的角色vo
 * @author ljy
 *
 */
public class UserRoleVo extends BasicVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
   /**
     * id
     */
    private Integer id;

    /**
     * 用户编号
     */
    private String userNumber;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPwd;

    /**
     * 帐号类型  1001：普通用户 
     */
    private String accountType;

    /**
     * 积分
     */
    private Long integral;

    /**
     * 状态 1001：禁用   1002：正常
     */
    private String status;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 注册时间
     */
    private Date createDate;
    
    /**
     * 用户角色
     */
    private Set<SysUserRoleEntity> userRoleList;

    /**
     * 用户拥有的资源
     */
    private List<String> menusList;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Long getIntegral() {
		return integral;
	}

	public void setIntegral(Long integral) {
		this.integral = integral;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Set<SysUserRoleEntity> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(Set<SysUserRoleEntity> userRoleList) {
		this.userRoleList = userRoleList;
	}

	public List<String> getMenusList() {
		return menusList;
	}

	public void setMenusList(List<String> menusList) {
		this.menusList = menusList;
	}
    
    

}
