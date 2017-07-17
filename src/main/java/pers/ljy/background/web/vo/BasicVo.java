package pers.ljy.background.web.vo;

import java.io.Serializable;

public abstract class BasicVo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//主键ID
	private Integer id;
	
	private Integer page = 1;
	
	private Integer pageSize = 20;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
}
