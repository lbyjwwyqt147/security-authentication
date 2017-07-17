package pers.ljy.background.web.controller.authority;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pers.ljy.background.model.SysUserRoleEntity;
import pers.ljy.background.service.authority.SysUserRoleService;
import pers.ljy.background.share.result.ApiResultView;
import pers.ljy.background.web.controller.BasicController;

@RestController
public class UserRolerController extends BasicController {

	@Autowired
	private SysUserRoleService userRoleService;
	
	@PostMapping(value="/userRolers")
	public ApiResultView save(SysUserRoleEntity userRole){
		this.userRoleService.insert(userRole);
		return this.buildDefaultDatePacket();
	}
	
	@GetMapping(value="/userRolers")
	public ApiResultView list(){
		CopyOnWriteArrayList<SysUserRoleEntity> list = this.userRoleService.selectAll();
		return this.buildDataPacket(list);
	}
}
