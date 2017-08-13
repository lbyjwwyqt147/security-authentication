package pers.ljy.background.web.controller.authority;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pers.ljy.background.model.SysRoleEntity;
import pers.ljy.background.service.authority.SysRoleService;
import pers.ljy.background.share.dto.PageForm;
import pers.ljy.background.share.result.ApiResultCode;
import pers.ljy.background.share.result.ApiResultView;
import pers.ljy.background.web.controller.BasicController;
import pers.ljy.background.web.vo.authority.RoleVo;

/***
 * 文件名称: RoleController.java
 * 文件描述: 角色 controller
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年07月17日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@RestController
public class RoleController extends BasicController{
	
	@Autowired
	private SysRoleService roleService;
	
	/**
	 * 保存角色
	 * @param role
	 * @return
	 */
	@PostMapping(value="/roles")
	public ApiResultView save(SysRoleEntity role){
		int status = ApiResultCode.FAIL.getCode();
		String msg = ApiResultCode.FAIL.getMsg();
		role.setCreateDate(new Date());
		role.setCreateUserId(1);
		int count = this.roleService.insert(role);
		if(count > 0){
			return this.buildDefaultDatePacket();
		}
		return this.buildRestful(status, msg, null);
	}
	
	/**
	 * 角色列表
	 * @param from
	 * @return
	 */
	@GetMapping(value="/roles")
	public String list(RoleVo from){
		CopyOnWriteArrayList<SysRoleEntity> list = this.roleService.selectAll();
		PageForm<SysRoleEntity> pageForm = new PageForm<SysRoleEntity>();
		pageForm.setTotal(10);
		pageForm.setRows(list);
		return this.buildListsData(pageForm);
	}
	
	

	/**
	 * 已分配角色
	 * @return
	 */
	@GetMapping(value="/userRolers/y")
	public String ylist(int userId,HttpSession httpSession){
		CopyOnWriteArrayList<SysRoleEntity> list = this.roleService.selectUserRoleByUserIdIn(userId);
		PageForm<SysRoleEntity> pageForm = new PageForm<SysRoleEntity>();
		pageForm.setTotal(10);
		pageForm.setRows(list);
		return this.buildListsData(pageForm);
	}
	
	/**
	 * 未配角色
	 * @return
	 */
	@GetMapping(value="/userRolers/n")
	public String list(int userId){
		CopyOnWriteArrayList<SysRoleEntity> list = this.roleService.selectUserRoleByUserIdNotIn(userId);
		PageForm<SysRoleEntity> pageForm = new PageForm<SysRoleEntity>();
		pageForm.setTotal(10);
		pageForm.setRows(list);
		
		return this.buildListsData(pageForm);
	}

}
