/**
 *
 */
package com.baiyi.jj.app.entity;

import com.baiyi.core.database.AbstractBaseModel;


/**
 * @author tangkun
 *
 */
public class NewsDetailCommentEntity extends AbstractBaseModel{

	// ����id
	private String ComId;
	// �û�id
	private String comUserId;
	// �û���
	private String comUserName;
	// �û�ͷ��
	private String comUserHead;
	// ��������
	private String comContent;
	// ����ʱ��
	private String comTime;
	// ֧����
	private int comAgree;
	// ������
	private int comOpposition;
	// �Ƿ�����
	private boolean isAnonymous;
	// ����������
	private int comNum;
	// ÿ�����۵�������Ŀ
	private int comComNum;
	//����������
	private int newsAllCount;
	// ����id
	private int articleId;
	private boolean isNotUtc;

	public boolean isNotUtc() {
		return isNotUtc;
	}

	public void setNotUtc(boolean notUtc) {
		isNotUtc = notUtc;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getComUserId() {
		return comUserId;
	}
	public void setComUserId(String comUserId) {
		this.comUserId = comUserId;
	}
	public int getComComNum() {
		return comComNum;
	}
	public void setComComNum(int comComNum) {
		this.comComNum = comComNum;
	}
	public int getComNum() {
		return comNum;
	}
	public void setComNum(int comNum) {
		this.comNum = comNum;
	}
	public String getComUserName() {
		return comUserName;
	}
	public void setComUserName(String comUserName) {
		this.comUserName = comUserName;
	}
	public String getComTime() {
		return comTime;
	}
	public void setComTime(String comTime) {
		this.comTime = comTime;
	}
	public String getComUserHead() {
		return comUserHead;
	}
	public void setComUserHead(String comUserHead) {
		this.comUserHead = comUserHead;
	}
	public String getComContent() {
		return comContent;
	}
	public void setComContent(String comContent) {
		this.comContent = comContent;
	}
	public String getComId() {
		return ComId;
	}
	public void setComId(String comId) {
		ComId = comId;
	}

	public int getComAgree() {
		return comAgree;
	}
	public void setComAgree(int comAgree) {
		this.comAgree = comAgree;
	}
	public int getComOpposition() {
		return comOpposition;
	}
	public void setComOpposition(int comOpposition) {
		this.comOpposition = comOpposition;
	}
	public boolean isAnonymous() {
		return isAnonymous;
	}
	public void setAnonymous(boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	public int getNewsAllCount() {
		return newsAllCount;
	}
	public void setNewsAllCount(int newsAllCount) {
		this.newsAllCount = newsAllCount;
	}


}
