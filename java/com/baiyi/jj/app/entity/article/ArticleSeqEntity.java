package com.baiyi.jj.app.entity.article;

import com.baiyi.core.database.AbstractBaseModel;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *  单一列表索引
 *
 */
@DatabaseTable(tableName = "tb_articleindexs")
public class ArticleSeqEntity extends AbstractBaseModel{

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private long time;
	@DatabaseField
	private String channelseq;

	public ArticleSeqEntity() {
	}

	public ArticleSeqEntity(long time, String channelseq) {
		this.time = time;
		this.channelseq = channelseq;
	}

	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getChannelseq() {
		return channelseq;
	}
	public void setChannelseq(String channelseq) {
		this.channelseq = channelseq;
	}
	
}
