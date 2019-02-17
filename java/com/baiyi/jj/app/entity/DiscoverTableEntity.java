package com.baiyi.jj.app.entity;

import com.baiyi.core.database.AbstractBaseModel;

public class DiscoverTableEntity extends AbstractBaseModel{
	
	private String tableName;
	private String tableNameStr; //°æ¿éÃû³Æ
	private int tableRes;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableNameStr() {
		return tableNameStr;
	}
	public void setTableNameStr(String tableNameStr) {
		this.tableNameStr = tableNameStr;
	}
	public int getTableRes() {
		return tableRes;
	}
	public void setTableRes(int tableRes) {
		this.tableRes = tableRes;
	}
	
	
	

}
