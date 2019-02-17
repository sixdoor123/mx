package com.baiyi.cmall.activities.main.business.entity;

import java.util.List;

import com.baiyi.core.database.AbstractBaseModel;

public class FeedBackEntity extends AbstractBaseModel{
	
	private String reply_user;
	private String reply_time;
	private String created_at;
	private String question;
	private String create_user;
	private String answer;
	private String feedId;
	private List<String> imgList;
	
	
	// 类型
	private int userType; // 1,系统  2，用户
	
//	
	
	
	
	public String getReply_user() {
		return reply_user;
	}
	public List<String> getImgList() {
		return imgList;
	}
	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}
	public void setReply_user(String reply_user) {
		this.reply_user = reply_user;
	}
	public String getReply_time() {
		return reply_time;
	}
	public void setReply_time(String reply_time) {
		this.reply_time = reply_time;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getFeedId() {
		return feedId;
	}
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	
	

}
