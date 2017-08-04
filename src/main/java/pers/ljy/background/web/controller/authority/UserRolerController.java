package pers.ljy.background.web.controller.authority;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.ljy.background.service.authority.SysUserRoleService;
import pers.ljy.background.share.logs.SystemControllerLog;
import pers.ljy.background.share.result.ApiResultView;
import pers.ljy.background.web.controller.BasicController;

@RestController
public class UserRolerController extends BasicController {

	@Autowired
	private SysUserRoleService userRoleService;
	
	/**
	 * 分配角色
	 * @param userRole
	 * @return
	 */
	@SystemControllerLog(description="给用户分配角色")
	@PostMapping(value="/userRolers")
	public ApiResultView save(Integer userId,String roleIds){
		List<Integer> roleIdsList = new ArrayList<>();
		String[] roleIdArray = roleIds.split(",");
		for (String roleId : roleIdArray) {
			roleIdsList.add(Integer.valueOf(roleId));
		}
		this.userRoleService.batchInsert(userId, roleIdsList);
		return this.buildDefaultDatePacket();
	}
	
	/**
	 * 移除分配角色
	 * @param userRole
	 * @return
	 */
	@SystemControllerLog(description="移除用户分配的角色")
	@DeleteMapping(value="/userRolers")
	public ApiResultView dels(Integer userId,String roleIds){
		CopyOnWriteArrayList<Integer> roleIdsList = new CopyOnWriteArrayList<>();
		String[] roleIdArray = roleIds.split(",");
		for (String roleId : roleIdArray) {
			roleIdsList.add(Integer.valueOf(roleId));
		}
		this.userRoleService.batchDeletes(userId, roleIdsList);
		return this.buildDefaultDatePacket();
	}
	
}
