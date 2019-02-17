package com.baiyi.jj.app.entity;

import com.baiyi.core.database.AbstractBaseModel;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/** 
 * ITEM�Ķ�Ӧ���򻯶�������
 *  */
@DatabaseTable(tableName = "tb_channel")
public class ChannelItem extends AbstractBaseModel {

	@DatabaseField(generatedId = true)
	private int id;
	// Ƶ��id
	@DatabaseField
	private String cid;
	// Ƶ������
	@DatabaseField
	private String channel_name;
	// Ƶ�����
	@DatabaseField
	private String channel_group;
	// �Ƿ���
	@DatabaseField
	private String is_default;
	@DatabaseField
	private String channel_type;
	@DatabaseField
	private String channel_seq;
	//��ҳ����
	@DatabaseField
	private String convert_img;
	//Ƶ������
	@DatabaseField
	private int channel_index;
	//���û�ȡ
	@DatabaseField
	private String mid;
	@DatabaseField
	private String recommend;  // 用来判断是不是有tag的


	public ChannelItem() {
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}
	public String getChannel_seq() {
		return channel_seq;
	}
	public void setChannel_seq(String channel_seq) {
		this.channel_seq = channel_seq;
	}
	public String getChannel_group() {
		return channel_group;
	}
	public void setChannel_group(String channel_group) {
		this.channel_group = channel_group;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getChannel_name() {
		return channel_name;
	}
	public String getIs_default() {
		return is_default;
	}
	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}
	public String getChannel_type() {
		return channel_type;
	}
	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}
	public String getConvert_img() {
		return convert_img;
	}
	public void setConvert_img(String convert_img) {
		this.convert_img = convert_img;
	}
	public int getChannel_index() {
		return channel_index;
	}
	public void setChannel_index(int channel_index) {
		this.channel_index = channel_index;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
}