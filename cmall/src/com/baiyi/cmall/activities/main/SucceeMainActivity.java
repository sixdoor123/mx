package com.baiyi.cmall.activities.main;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * �ɹ�����
 * 
 * @author sunxy
 * 
 */
public class SucceeMainActivity extends BaseMsgActivity implements OnClickListener {

	// ���ع�Ӧ�б�İ�ť
	private TextView mBtnSuccessBack;

	// ���˼���ͼ��
	@SuppressWarnings("unused")
	private ImageView mImgSuccessBack;

	// �ɹ���Ϣ
	private TextView mTxtSeccessHint;

	// ѡ�����ĸ��ĳɹ�����
	// 0 ί�вɹ� ����TWO
	// 1 ί�й�Ӧ ����THREE
	// 2��Ӧ������ ����TWO
	// 3�ɹ������� ����THREE
	private int select;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(false);
		initData();
		initTitle();
		initContent();
	}

	/**
	 * �õ�����
	 */
	private void initData() {
		select = getIntent().getIntExtra("temp", 0);
	}

	/**
	 * ��ʼ������
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_delegate_success, null);
		win_content.addView(view);
		mBtnSuccessBack = (TextView) view.findViewById(R.id.btn_success_back);
		mTxtSeccessHint = (TextView) view.findViewById(R.id.tv_success_hint);
		switch (select) {
		case 0:
			mTxtSeccessHint.setText("ί�вɹ��ɹ�");
			mBtnSuccessBack.setText("���ع�Ӧ�б�");
			break;
		case 1:
			mTxtSeccessHint.setText("ί�й�Ӧ�ɹ�");
			mBtnSuccessBack.setText("���زɹ��б�");
			break;
		case 2:
			mTxtSeccessHint.setText("��Ӧ���ύ�ɹ�");
			mBtnSuccessBack.setText("���ع�Ӧ�б�");
			break;
		case 3:
			mTxtSeccessHint.setText("�ɹ����ύ�ɹ�");
			mBtnSuccessBack.setText("���زɹ��б�");
			break;

		default:
			break;
		}
		mBtnSuccessBack.setOnClickListener(this);
	}

	/**
	 * ������
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		switch (select) {
		case 0:
			topTitleView.setEventName("�ɹ��ɹ�");
			break;
		case 1:
			topTitleView.setEventName("��Ӧ�ɹ�");
			break;
		case 2:
			topTitleView.setEventName("��Ӧ��");
			break;
		case 3:
			topTitleView.setEventName("�ɹ���");
			break;

		default:
			break;
		}
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt, MsgItemUtil.Pop_Default_img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state, SucceeMainActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_success_back:
			back();
			break;
		default:
			break;
		}
	}

	/**
	 * ���ع�Ӧ(�ɹ�)�б�İ�ť�ĵ���¼�
	 */
	private void back() {
		switch (select) {
		case 0:
			backHomePage("THREE");
			break;
		case 1:
			backHomePage("FOUR");
			break;
		case 2:
			backHomePage("THREE");
			break;
		case 3:
			backHomePage("FOUR");
			break;
		default:
			break;
		}
	}

}
