/**
 * 
 */
package com.baiyi.jj.app.entity.article;

import com.baiyi.core.database.AbstractBaseModel;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * 新闻 频道信息
 * @author tangkun
 *
 */
@DatabaseTable(tableName = "tb_channeltrend")
public class ArticleChannel implements Serializable{
	@DatabaseField(generatedId = true)
	private int id;
	//频道id
	@DatabaseField
	private int channel_id;
	@DatabaseField
	private int trend_version;
	@DatabaseField
	private int trend_index;
	@DatabaseField
	private int new_version;
	@DatabaseField
	private int new_index;
	@DatabaseField
	private String sub;
	@DatabaseField
	private String tableName;
	//是否提交过该频道 false true
	@DatabaseField
	private String isSub;

	public ArticleChannel() {
	}


	public int getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}
	public int getTrend_version() {
		return trend_version;
	}
	public void setTrend_version(int trend_version) {
		this.trend_version = trend_version;
	}
	public int getTrend_index() {
		return trend_index;
	}
	public void setTrend_index(int trend_index) {
		this.trend_index = trend_index;
	}
	public int getNew_version() {
		return new_version;
	}
	public void setNew_version(int new_version) {
		this.new_version = new_version;
	}
	public int getNew_index() {
		return new_index;
	}
	public void setNew_index(int new_index) {
		this.new_index = new_index;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getIsSub() {
		return isSub;
	}
	public void setIsSub(String isSub) {
		this.isSub = isSub;
	}

}
