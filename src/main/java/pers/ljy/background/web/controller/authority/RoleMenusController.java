package pers.ljy.background.web.controller.authority;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pers.ljy.background.service.authority.SysRoleMenuService;
import pers.ljy.background.share.result.ApiResultView;
import pers.ljy.background.web.controller.BasicController;

@RestController
public class RoleMenusController extends BasicController {

	@Autowired
	private SysRoleMenuService roleMenuService;
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	@PostMapping(value="/roleMenus")
	public ApiResultView save(@RequestParam(value="roleId",required=true)Integer roleId,@RequestParam(value="menusId",required=true)String menusId){
		String[] menusIds = menusId.split(",");
		CopyOnWriteArrayList<Integer> menusArrayList = new CopyOnWriteArrayList<>();
		for (String menuId : menusIds) {
			menusArrayList.add(Integer.valueOf(menuId));
		}
		this.roleMenuService.batchInset(roleId, menusArrayList);
		return this.buildDefaultDatePacket();
	}
	
	
	/**
	 * 删除
	 * @param entity
	 * @return
	 */
	@DeleteMapping(value="/roleMenus")
	public ApiResultView dels(@RequestParam(value="roleId",required=true)Integer roleId,@RequestParam(value="menusId",required=true)String menusId){
		String[] menusIds = menusId.split(",");
		CopyOnWriteArrayList<Integer> menusArrayList = new CopyOnWriteArrayList<>();
		for (String menuId : menusIds) {
			menusArrayList.add(Integer.valueOf(menuId));
		}
		this.roleMenuService.batchDelete(roleId, menusArrayList);
		return this.buildDefaultDatePacket();
	}
	
	
}
