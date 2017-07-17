package pers.ljy.background.web.controller.authority;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pers.ljy.background.model.SysRoleMenuEntity;
import pers.ljy.background.service.authority.SysRoleMenuService;
import pers.ljy.background.share.result.ApiResultView;
import pers.ljy.background.web.controller.BasicController;

@RestController
public class RoleMenusController extends BasicController {

	@Autowired
	private SysRoleMenuService roleMenuService;
	
	@PostMapping(value="/roleMenus")
	public ApiResultView save(SysRoleMenuEntity entity){
		this.roleMenuService.insert(entity);
		return this.buildDefaultDatePacket();
	}
	
	
	@GetMapping(value="/roleMenus")
	public ApiResultView list(){
		CopyOnWriteArrayList<SysRoleMenuEntity> list = this.roleMenuService.selectAll();
		return this.buildDataPacket(list);
	}
}
