package com.baiyi.cmall.activities.main.home_pager.entity;

import java.io.Serializable;

/**
 *  ��Ѷʵ����
 * @author lizl
 *
 */
public class ZiXunEntity implements Serializable{

	//ID
	private String id;
	//����
	private String title;
	//����
	private String content;
	//����
	private String type;
	//����
	private String name;
	//ʱ��
	private String time;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
	
}
