package com.baiyi.cmall.entity;

import java.io.Serializable;

/**
 *  �����б�����Լ�״̬��ʵ��
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-25 
 *       ����2:55:15
 */
public class IntentionTypeInfo implements Serializable{

	//���������Լ�״̬
	private String content ;
	//��Ӧ������
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
