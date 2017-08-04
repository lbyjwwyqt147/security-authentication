package pers.ljy.background.service.authority.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.ljy.background.mapper.SysResourceMenusDao;
import pers.ljy.background.model.SysResourceMenusEntity;
import pers.ljy.background.service.authority.SysResourceMenusService;
import pers.ljy.background.share.component.tree.JsTree;
import pers.ljy.background.share.component.tree.JsTreeState;
import pers.ljy.background.share.dao.BaseDao;
import pers.ljy.background.share.service.impl.BaseServiceImpl;

/***
 * 文件名称: SysResourceMenusServiceImpl.java
 * 文件描述: 资源菜单service接口 实现
 * 公 司: 
 * 内容摘要: 
 * 其他说明:
 * 完成日期:2017年06月12日 
 * 修改记录:
 * @version 1.0
 * @author ljy
 */
@Service
public class SysResourceMenusServiceImpl extends BaseServiceImpl<SysResourceMenusEntity, Integer> implements SysResourceMenusService {

	@Autowired
	private SysResourceMenusDao sysResourceMenusDao;
	
	@Override
	public BaseDao<SysResourceMenusEntity, Integer> getDao() {
		return sysResourceMenusDao;
	}

	@Override
	public CopyOnWriteArrayList<SysResourceMenusEntity> selectByPid(String pid) {
		return this.sysResourceMenusDao.selectByPid(pid);
	}

	@Override
	public String selectMaxPid(String pid) {
		return this.sysResourceMenusDao.selectMaxPid(pid);
	}

	@Override
	public String newestNumber(String pid) {
		StringBuffer newestNumberBuffer = new StringBuffer();
		String maxPid = this.selectMaxPid(pid);
		if(StringUtils.isEmpty(pid)){
			newestNumberBuffer.append(pid).append("1001");
		}else if(pid.trim().equals("1")){
			newestNumberBuffer.append("1001");
		}else {
			AtomicLong number = new AtomicLong(Long.valueOf(maxPid));
			number.addAndGet(1);
			newestNumberBuffer.append(number);
		}
		return newestNumberBuffer.toString();
	}

	@Override
	public JsTree menusTree(String pid) {
		JsTree root = new JsTree();
		root.setId("-1");
		//root.setParent("-1");
		//root.setText("资源菜单");
		root.setText("root");
		JsTreeState jsTreeState = new JsTreeState();
		jsTreeState.setSelected(true);
		ConcurrentMap<String, Object> rootAttr = new ConcurrentHashMap<>();
		rootAttr.put("pid", "1");
		rootAttr.put("bid", "1");
		root.setA_attr(rootAttr);
		
		root.setState(jsTreeState);
		
		
		JsTree tree1 =  new JsTree();
		tree1.setId("-2");
	//	tree1.setParent("-2");
		tree1.setText("节点1");
		
		//root.add(tree1);
		chlidrens(root,pid);
		
		return root;
	}
	
	private JsTree chlidrens(JsTree root,String pid){
		CopyOnWriteArrayList<SysResourceMenusEntity> chlidrenList = this.selectByPid(pid);
		chlidrenList.forEach(item -> {
			JsTree chlidren = new JsTree();
            chlidren.setId(item.getId().toString());
            chlidren.setText(item.getMenuName());
            root.add(chlidren);
            JsTree chlid = chlidrens(root, item.getMenuNumber());
		});
		return root;
	}

	
}
