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
		if(StringUtils.isEmpty(maxPid)){
			newestNumberBuffer.append(pid).append("1001");
		}else if(pid.trim().equals("1") && StringUtils.isEmpty(maxPid)){
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
		//root.setText("资源菜单");
		root.setText("root");
		JsTreeState jsTreeState = new JsTreeState();
		jsTreeState.setSelected(true);
		jsTreeState.setOpened(true);
		ConcurrentMap<String, Object> rootAttr = new ConcurrentHashMap<>();
		rootAttr.put("pid", "1");
		rootAttr.put("bid", "1");
		root.setA_attr(rootAttr);
		root.setState(jsTreeState);
		chlidrens(root,pid);
		
		return root;
	}
	
	/**
	 * 递归构建树结构
	 * @param root
	 * @param pid
	 * @return
	 */
	private JsTree chlidrens(JsTree root,String pid){
		CopyOnWriteArrayList<SysResourceMenusEntity> chlidrenList = this.selectByPid(pid);
		chlidrenList.forEach(item -> {
			JsTree chlidren = new JsTree();
            chlidren.setId(item.getId().toString());
            chlidren.setText(item.getMenuName());
        	ConcurrentMap<String, Object> chlidrenAttr = new ConcurrentHashMap<>();
        	chlidrenAttr.put("pid", item.getParentMenuNumber());
        	chlidrenAttr.put("bid", item.getMenuNumber());
    		chlidren.setA_attr(chlidrenAttr);
    		root.add(chlidren);
    		//递归
            chlidrens(chlidren, item.getMenuNumber());            
		});
		return root;
	}

	@Override
	public CopyOnWriteArrayList<SysResourceMenusEntity> selectByForm(SysResourceMenusEntity form) {
		return this.sysResourceMenusDao.selectByForm(form);
	}

	@Override
	public CopyOnWriteArrayList<SysResourceMenusEntity> selectByMenuTypeNotIn() {
		return this.sysResourceMenusDao.selectByMenuTypeNotIn();
	}

	@Override
	public CopyOnWriteArrayList<SysResourceMenusEntity> selectByPidLike(String pid) {
		return this.sysResourceMenusDao.selectByPidLike(pid);
	}

	@Override
	public CopyOnWriteArrayList<SysResourceMenusEntity> selectByRoleIdIn(Integer roleId,String menuType) {
		return this.sysResourceMenusDao.selectByRoleIdIn(roleId,menuType);
	}

	@Override
	public CopyOnWriteArrayList<SysResourceMenusEntity> selectByRoleIdNotIn(Integer roleId) {
		return this.sysResourceMenusDao.selectByRoleIdNotIn(roleId);
	}

	@Override
	public JsTree roleMenusIn(Integer roleId) {
		CopyOnWriteArrayList<SysResourceMenusEntity> list = this.selectByRoleIdIn(roleId,null);
		JsTree jsTree = this.roleMenus(list);
		return jsTree;
	}

	@Override
	public JsTree roleMenusNotIn(Integer roleId) {
		CopyOnWriteArrayList<SysResourceMenusEntity> list = this.selectByRoleIdNotIn(roleId);
		JsTree jsTree = this.roleMenus(list);
		return jsTree;
	}
	
	/**
	 * 构建角色资源树
	 * @param list
	 * @return
	 */
	private JsTree roleMenus(CopyOnWriteArrayList<SysResourceMenusEntity> list){
		JsTree root = new JsTree();
		root.setId("-1");
		//root.setText("资源菜单");
		root.setText("root");
		JsTreeState jsTreeState = new JsTreeState();
		jsTreeState.setOpened(true);
		jsTreeState.setDisabled(true);
		root.setState(jsTreeState);
		
		if(list != null && !list.isEmpty()){
			list.forEach(it -> {
				if(it.getParentMenuNumber().equals("1")){
					JsTree chlidren = new JsTree();
		            chlidren.setId(it.getId().toString());
		            chlidren.setText(it.getMenuName());
		        	ConcurrentMap<String, Object> chlidrenAttr = new ConcurrentHashMap<>();
		        	chlidrenAttr.put("pid", it.getParentMenuNumber());
		        	chlidrenAttr.put("bid", it.getMenuNumber());
		    		chlidren.setA_attr(chlidrenAttr);
		    		root.add(findChildren(chlidren, list));
				}
			});
			
		}
		return root;
	}
	
	/** 
     * 递归查找子节点 
     * @param treeNodes 
     * @return 
     */  
    @SuppressWarnings("unchecked")
	public static JsTree findChildren(JsTree treeNode,CopyOnWriteArrayList<SysResourceMenusEntity> list) {  
        ConcurrentMap<String, Object> attrMap = (ConcurrentMap<String, Object>) treeNode.getA_attr();
        list.forEach(it -> { 
            if(attrMap.get("bid").equals(it.getParentMenuNumber())) {  
            	JsTree chlidren = new JsTree();
	            chlidren.setId(it.getId().toString());
	            chlidren.setText(it.getMenuName());
	            chlidren.setIcon(it.getMenuIcon());
	        	ConcurrentMap<String, Object> chlidrenAttr = new ConcurrentHashMap<>();
	        	chlidrenAttr.put("pid", it.getParentMenuNumber());
	        	chlidrenAttr.put("bid", it.getMenuNumber());
	        	chlidrenAttr.put("parentId", treeNode.getId());
	    		chlidren.setA_attr(chlidrenAttr); 
                treeNode.add(findChildren(chlidren,list));  
            }  
        });  
        return treeNode;  
    }

	@Override
	public CopyOnWriteArrayList<JsTree> userMenus(String userId, String token) {
		CopyOnWriteArrayList<JsTree> userMenusList = new CopyOnWriteArrayList<>();
		CopyOnWriteArrayList<SysResourceMenusEntity> list = this.selectByRoleIdIn(1,"1002");
		if(list != null && !list.isEmpty()){
			list.forEach(it -> {
				if(it.getParentMenuNumber().equals("1")){
					JsTree chlidren = new JsTree();
		            chlidren.setId(it.getId().toString());
		            chlidren.setText(it.getMenuName());
		            chlidren.setIcon(it.getMenuIcon());
		        	ConcurrentMap<String, Object> chlidrenAttr = new ConcurrentHashMap<>();
		        	chlidrenAttr.put("pid", it.getParentMenuNumber());
		        	chlidrenAttr.put("bid", it.getMenuNumber());
		    		chlidren.setA_attr(chlidrenAttr);
		    		userMenusList.add(findChildren(chlidren, list));
				}
			});
			
		}
		return userMenusList;
	}  

	
}
