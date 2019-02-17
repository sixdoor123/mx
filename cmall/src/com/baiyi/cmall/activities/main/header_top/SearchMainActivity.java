package com.baiyi.cmall.activities.main.header_top;

import android.content.SharedPreferences;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.adapter.HistoryAdapter;
import com.baiyi.cmall.adapter.HistoryAdapter.OnBtnDeleteClickListener;
import com.baiyi.cmall.dialog.MsgBoxNoticeE.MsgBoxEOnClickListener;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.views.itemview.SearchView;
import com.baiyi.cmall.views.itemview.SearchView.OnResetPressClickListener;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;

/**
 * �ؼ�������������
 * 
 * @author lizl
 * 
 */
public class SearchMainActivity extends BaseActivity implements
		OnClickListener, OnResetPressClickListener {

	// ���������
	private SearchView mEtSearch;
	// ������ť
	private TextView mTvSearch;
	// ��ʷ��¼�б�
	private PullToRefreshListView listView;
	// ����
	private LinearLayout mImgBack;
	// ������
	private HistoryAdapter adapter;
	// ����Դ
	private ArrayList<String> histories = new ArrayList<String>();

	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	// ��ʷ��¼���ַ��ܼ�
	private String historyString;

	// ��ʷ��¼�����·��
	private static final String HIS_URL = "History_Url";
	// ��ʷ��¼�����·��
	private static final String FIELD_ADDRESS = "history";

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(false);

		initTitle();
		initContent();
	}

	/**
	 * ��ʼ������
	 */
	private void initTitle() {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			ContextUtil.getLayoutInflater(this).inflate(
					R.layout.main_title_item_h, win_title);
		} else {
			ContextUtil.getLayoutInflater(this).inflate(
					R.layout.main_title_item_l, win_title);
		}

		mImgBack = (LinearLayout) findViewById(R.id.img_back);
		mEtSearch = (SearchView) findViewById(R.id.et_search);
		mTvSearch = (TextView) findViewById(R.id.tv_search);
		mImgBack.setOnClickListener(this);
		mTvSearch.setOnClickListener(this);
		mEtSearch.setResetListener(this);
	}

	/**
	 * ��ʼ������
	 */
	private void initContent() {

		ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_search,
				win_content);

		listView = (PullToRefreshListView) findViewById(R.id.lst_search_history);

		/*
		 * ��listView�ĵײ������հ�ť
		 */
		View emptyView = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.item_foot_orange, null);
		TextView mBtnEmpty = (TextView) emptyView.findViewById(R.id.btn_empty);
		listView.getRefreshableView().addFooterView(emptyView);

		mBtnEmpty.setOnClickListener(this);

		initAutoComplete();
		setTextChange();
	}

	private ArrayList<String> temps = new ArrayList<String>();

	/**
	 * ���������ڵ����ָı�ʱ������ƥ����Ӧ������
	 * 
	 */
	private void setTextChange() {

		mEtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (!TextUtils.isEmpty(s)) {
					temps.clear();
					for (int i = 0; i < histories.size(); i++) {
						if (s.length() <= histories.get(i).length()
								&& (histories.get(i).contains(s) || histories
										.get(i).equals(s))) {
							if (i < 5) {
								temps.add(histories.get(i));
							} else {
								break;
							}
						}
					}
					adapter.setData(temps);
					if (TextUtils.isEmpty(s)) {

						listView.setVisibility(View.VISIBLE);
					}
				} else {
					initSharePreferences();
					adapter.setData(histories);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private String textString;

	/**
	 * ��ʼ��������ɵ���ʷ��¼
	 * 
	 * @param fieldAdress
	 * @param listView
	 */
	private void initAutoComplete() {

		initSharePreferences();

		adapter = new HistoryAdapter(this);

		adapter.setListener(new OnBtnDeleteClickListener() {

			@Override
			public void onBtnDelete(int position, String info) {
				// ����ɾ��������ʷ��¼
				deleteHistory(position, info);
			}

			@Override
			public void onBtnDeleteAll() {
				// ����ɾ��ȫ����ʷ��¼
				showDia();
			}

			@Override
			public void onSetSearchInfo(String info) {
				textString = info;
				// ����������Ϣ
//				goActivity(MallCagetoryActivity.class, info, 1);
				IntentClassChangeUtils.startMallCagetoryActivity(SearchMainActivity.this, info,1);
				savedState(info);
			}

			@Override
			public void onBtnShow(Boolean isShow) {
				// ������հ�ť����ʾ������
				if (isShow) {
					win_content.setVisibility(View.VISIBLE);
				} else {
					win_content.setVisibility(View.GONE);
				}
			}

		});

		listView.setAdapter(adapter);
		adapter.setData(histories);
	}

	private int size = 0;

	/**
	 * ɾ��������Ŀ����ʷ��¼
	 * 
	 * @param info
	 */
	private void deleteHistory(int position, String info) {

		initSharePreferences();
		editor.clear().commit();

		size = histories.size();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < histories.size(); i++) {
			if (i < 5) {
				if (i == position) {
					histories.remove(position);
				}
			} else {
				break;
			}
			count++;
		}
		if (count == 1 && size == 1) {
			adapter.setData(histories);
			editor.remove(FIELD_ADDRESS).commit();
		} else {
			adapter.setData(histories);
			for (String string : histories) {
				builder.append(string).append(",");
			}
			editor.putString(FIELD_ADDRESS, builder.toString()).commit();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.tv_search:// ������������ʷ��¼��ˢ�����ݣ���ת����Ӧ����

			String serachString = mEtSearch.getText().toString().trim();
			if (TextUtils.isEmpty(serachString)) {
				displayToast("�ؼ��ֲ���Ϊ��");
				return;
			}
//			goActivity(GoodsMainActivity.class, serachString, 1);
			IntentClassChangeUtils.startMallCagetoryActivity(SearchMainActivity.this, serachString,1);
			savedState(mEtSearch.getText().toString().trim());
			finish();
			break;
		case R.id.img_back:
			finish();
			break;
		case R.id.btn_empty:// �����ʷ��¼
			showDia();
			break;

		}

	}

	private boolean flag = true;

	/**
	 * ����������¼
	 */
	protected void savedState(String textString) {

		if (!TextViewUtil.isStringEmpty(textString)) {
			initSharePreferences();
			StringBuilder builder = new StringBuilder();
			StringBuilder b = new StringBuilder();

			// û���ظ�����Ϣ��ֱ����ӵ�����
			if (!isHaveHistory(histories, textString)) {

				builder.append(textString).append(",");
				int c = (histories.size() > 4 ? 4 : histories.size());
				for (int i = 0; i < c; i++) {
					builder.append(histories.get(i)).append(",");
				}
				editor.putString(FIELD_ADDRESS, builder.toString()).commit();

			} else {// �ظ�����Ϣ��֮ǰ�ļ�¼�ö�
				b.append(textString).append(",");
				for (int i = 0; i < histories.size(); i++) {
					// builder.append(histories.get(i)).append(",");
					// ��ͬ
					if (!textString.equals(histories.get(i))) {
						// flag = false;
						b.append(histories.get(i)).append(",");
					}
				}
				// if (flag) {
				// b.append(textString).append(",").append(builder.toString());
				// }
				editor.putString(FIELD_ADDRESS, b.toString()).commit();
			}
		}

	}

	/**
	 * �ж������ظ�����ʷ��¼
	 * 
	 * @param strings
	 * @param textString
	 * @return
	 */
	private boolean isHaveHistory(ArrayList<String> strings, String textString) {

		for (String string : strings) {
			if (textString.equals(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ���������Ի���ɾ�����м�¼
	 */
	private void showDia() {

		// ����
		// ����
		// 2 �����ڵ��м���ʾ
		displayMsgBoxE("��ʾ", "�Ƿ�ɾ�����е���ʷ��¼?", new MsgBoxEOnClickListener() {

			@Override
			public void onClickListener() {
				initSharePreferences();
				editor.remove(FIELD_ADDRESS);
				editor.clear().commit();
				histories.clear();
				adapter.setData(new ArrayList<String>());
			}
		});
	}

	private int count = 0;

	/**
	 * ��ʼ���惦��ʽ
	 */
	private void initSharePreferences() {

		sp = getSharedPreferences(HIS_URL, 0);
		editor = sp.edit();
		historyString = sp.getString(FIELD_ADDRESS, "");
		// ����ʷ��¼���ٺŷֳ�����
		String[] strings = historyString.split(",");
		for (int i = 0; i < strings.length; i++) {
			if (!TextUtils.isEmpty(strings[i])) {
				if (!histories.contains(strings[i])) {
					histories.add(strings[i]);
				}
			}
		}
	}

	@Override
	public void onReset() {
		initSharePreferences();
		savedState(textString);
		adapter.setData(histories);
	}
}
