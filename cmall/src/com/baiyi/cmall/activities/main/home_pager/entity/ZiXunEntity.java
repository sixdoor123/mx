package com.baiyi.cmall.activities.main.home_pager.entity;

import java.io.Serializable;

/**
 *  资讯实体类
 * @author lizl
 *
 */
public class ZiXunEntity implements Serializable{

	//ID
	private String id;
	//标题
	private String title;
	//内容
	private String content;
	//类型
	private String type;
	//名称
	private String name;
	//时间
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
