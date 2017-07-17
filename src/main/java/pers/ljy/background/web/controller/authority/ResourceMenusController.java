package pers.ljy.background.web.controller.authority;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pers.ljy.background.model.SysResourceMenusEntity;
import pers.ljy.background.service.authority.SysResourceMenusService;
import pers.ljy.background.share.result.ApiResultView;
import pers.ljy.background.web.controller.BasicController;

@RestController
public class ResourceMenusController extends BasicController {

	@Autowired
	private SysResourceMenusService resourceMenusService;
	
	@PostMapping(value="/resourceMenus")
	public ApiResultView save(SysResourceMenusEntity entity){
		this.resourceMenusService.insert(entity);
		return this.buildDefaultDatePacket();
	}
	
	@GetMapping(value="/resourceMenus")
	public ApiResultView list(){
		CopyOnWriteArrayList<SysResourceMenusEntity> list = this.resourceMenusService.selectAll();
		return this.buildDataPacket(list);
	}
}
