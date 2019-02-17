package com.baiyi.cmall.entity;

import java.io.Serializable;

/**
 *  意向列表分类以及状态的实体
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-25 
 *       下午2:55:15
 */
public class IntentionTypeInfo implements Serializable{

	//意向类型以及状态
	private String content ;
	//对应的数字
	private String id;
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	public IntentionTypeInfo(){}
	public IntentionTypeInfo(String content, String id) {
		super();
		this.content = content;
		this.id = id;
	}
	
	
}
