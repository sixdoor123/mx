package com.baiyi.jj.app.entity;

import java.io.Serializable;

/**
 * @author tangkun
 *
 */
public class PicEntity implements Serializable{
	private String Details;
	private int ProjectID;
	private String ProjectPath;
	private int SortNum;
	private int Type;
	private int id;
	public String getDetails() {
		return Details;
	}
	public void setDetails(String details) {
		Details = details;
	}
	public int getProjectID() {
		return ProjectID;
	}
	public void setProjectID(int projectID) {
		ProjectID = projectID;
	}
	public String getProjectPath() {
		return ProjectPath;
	}
	public void setProjectPath(String projectPath) {
		ProjectPath = projectPath;
	}
	public int getSortNum() {
		return SortNum;
	}
	public void setSortNum(int sortNum) {
		SortNum = sortNum;
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
