package com.baiyi.jj.app.activity.attention;

import com.baiyi.core.database.AbstractBaseModel;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * ���ߣ�lizl on 2016/11/28 11:10
 */
@DatabaseTable(tableName = "tb_attetion")
public class AttentionWordsEntity extends AbstractBaseModel {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String words;
    @DatabaseField
    private String channel_id;
    @DatabaseField
    private String channel_name;
    @DatabaseField
    private String alias;
    @DatabaseField
    private String icon;
    @DatabaseField
    private String created_at;
    @DatabaseField
    private boolean isAttet;
    @DatabaseField
    private int follow_id;
    @DatabaseField
    private int hotword_id;
    @DatabaseField
    private String mid;

    public AttentionWordsEntity() {
    }

    public int getHotword_id() {
        return hotword_id;
    }

    public void setHotword_id(int hotword_id) {
        this.hotword_id = hotword_id;
    }

    public int getFollow_id() {
        return follow_id;
    }

    public void setFollow_id(int follow_id) {
        this.follow_id = follow_id;
    }

    public boolean isAttet() {
        return isAttet;
    }

    public void setAttet(boolean attet) {
        isAttet = attet;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
