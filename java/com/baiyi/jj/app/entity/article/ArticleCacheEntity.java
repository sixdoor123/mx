/**
 * 
 */
package com.baiyi.jj.app.entity.article;

import com.baiyi.core.database.AbstractBaseModel;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * �����б���
 * @author tangkun
 *
 */
@DatabaseTable(tableName = "tb_articleindex")
public class ArticleCacheEntity extends AbstractBaseModel{

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String tableName;
	private String filePath;
	@DatabaseField
	private long time;

	public ArticleCacheEntity() {
	}

	public ArticleCacheEntity(long time, String tableName) {
		this.time = time;
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}

}
