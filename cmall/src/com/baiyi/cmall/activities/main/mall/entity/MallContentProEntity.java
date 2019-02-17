package com.baiyi.cmall.activities.main.mall.entity;

/**
 * 商城-列表单一项目
 * @author tangkun
 *
 */
public class MallContentProEntity {
	//id
	private int id;
	//图片地址
	private String url;
	//项目名称
	private String pn;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPn() {
		return pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}

}
