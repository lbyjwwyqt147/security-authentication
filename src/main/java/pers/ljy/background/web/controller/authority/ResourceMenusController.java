package pers.ljy.background.web.controller.authority;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import pers.ljy.background.model.SysResourceMenusEntity;
import pers.ljy.background.service.authority.SysResourceMenusService;
import pers.ljy.background.share.component.tree.JsTree;
import pers.ljy.background.share.logs.SystemControllerLog;
import pers.ljy.background.share.result.ApiResultView;
import pers.ljy.background.web.controller.BasicController;

@RestController
public class ResourceMenusController extends BasicController {

	@Autowired
	private SysResourceMenusService resourceMenusService;
	
	/**
	 * 保存资源菜单
	 * @param entity
	 * @return
	 */
	@SystemControllerLog(description="保存菜单资源")
	@PostMapping(value="/resourceMenus")
	public ApiResultView save(SysResourceMenusEntity entity){
		entity.setMenuNumber(this.resourceMenusService.newestNumber(entity.getParentMenuNumber()));
		entity.setCreateDate(new Date());
		entity.setCreateUserId(1);
		this.resourceMenusService.insert(entity);
		return this.buildDefaultDatePacket();
	}
	
	/**
	 * 菜单资源列表
	 * @param pid  父ID
	 * @return
	 */
	@SystemControllerLog(description="菜单资源列表")
	@GetMapping(value="/resourceMenus")
	public ApiResultView list(@RequestParam(value="pid",required=true) String pid){
		CopyOnWriteArrayList<SysResourceMenusEntity> list = this.resourceMenusService.selectByPid(pid);
		return this.buildDataPacket(list);
	}
	
	/**
	 * 资源菜单树
	 * @param pid
	 * @return
	 */
	@SystemControllerLog(description="资源菜单树")
	@GetMapping(value="/resourceMenus/tree")
	public String tree(@RequestParam(value="pid",required=true) String pid){
		JsTree tree = this.resourceMenusService.menusTree(pid);
		List<JsTree> list = new ArrayList<>();
		list.add(tree);
		return this.buildListsData(JSON.toJSON(list).toString());
	}
	
	/**
	 * 资源菜单树
	 * @param pid
	 * @return
	 */
	@SystemControllerLog(description="资源菜单树")
	@GetMapping(value="/resourceMenus/tree1")
	public ApiResultView tree1(@RequestParam(value="pid",required=true) String pid){
		JsTree tree = this.resourceMenusService.menusTree(pid);
		return this.buildDataPacket(tree);

	}
	
	@SystemControllerLog(description="资源菜单树")
	@GetMapping(value="/resourceMenus/tree2")
	public String tree2(@RequestParam(value="pid",required=true) String pid){
		JsTree tree = this.resourceMenusService.menusTree(pid);
		List<JsTree> list = new ArrayList<>();
		list.add(tree);
		return this.buildListsData(list);

	}
	

	/**
	 * 已分配资源
	 * @param roleId
	 * @return
	 */
	@GetMapping(value="/resourceMenus/y")
	public String treey(@RequestParam(value="roleId",required=true)Integer roleId){
		JsTree tree = this.resourceMenusService.roleMenusIn(roleId);
		List<JsTree> list = new ArrayList<>();
		list.add(tree);
		return this.buildListsData(list);
	}
	
	/**
	 * 未分配资源
	 * @param roleId
	 * @return
	 */
	@GetMapping(value="/resourceMenus/n")
	public String treen(@RequestParam(value="roleId",required=true)Integer roleId){
		JsTree tree = this.resourceMenusService.roleMenusNotIn(roleId);
		List<JsTree> list = new ArrayList<>();
		list.add(tree);
		return this.buildListsData(list);
	}
	
	/**
	 * 用户所属的资源菜单
	 * @param roleId
	 * @return
	 */
	@GetMapping(value="/resourceMenus/user")
	public String userMenus(){
		return this.buildListsData(this.resourceMenusService.userMenus("", ""));
	}
}
