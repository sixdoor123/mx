/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.home.itemview.DividerItemDecoration;
import com.baiyi.cmall.activities.main.mall.pop.adapter.FirstFilterAdapter;
import com.baiyi.cmall.activities.main.mall.pop.adapter.SeconedFilterAdapter;
import com.baiyi.cmall.activities.main.mall.pop.entity.Qcm;
import com.baiyi.cmall.activities.main.mall.pop.manager.FilterManager;
import com.baiyi.cmall.activities.main.mall.pop.manager.FilterNetUrl;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.model.Ftvm;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.pop.BasePopupWindow;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.port.OnRecycleViewItemClickListener;

/**
 * �̳�-ɸѡPop
 * 
 * @author tangkun
 *
 */
public class PopMallMenuFilter extends BasePopupWindow {

	private Context context = null;

	// һ��
	private TextView btnBack = null;
	private TextView btnOk = null;
	private TextView btnCancel = null;

	// �б�����
	private XRecyclerView mRecyclerView = null;
	// һ������������
	private FirstFilterAdapter<Qcm> FirstDapter = null;
	// һ����������
	private List<Qcm> qcms;

	private Map<Integer, List<Ftvm>> maps = null;

	/**
	 * @param contentView
	 * @param context
	 * @param height
	 */
	public PopMallMenuFilter(View contentView, Context context, int width, int height) {
		super(contentView, context, width, height);
		this.context = context;

		if (maps == null) {
			maps = new HashMap<Integer, List<Ftvm>>();
		}

		initFirstView(contentView);
		initSeconedView(contentView);
		loadData();
	}

	/**
	 * �������� http://u1q4567516.iask.in/cmallwebservice/Index/Mall/Fif
	 */
	private void loadData() {

		bar.setVisibility(View.VISIBLE);
		bar.setProgressInfo("������,���Ժ�...");
		bar.start();

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(FilterNetUrl.getFilfterFirstUrl());
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long curByteNum, long totalByteNum) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				((BaseActivity) context).displayToast( arg2);

				bar.setVisibility(View.GONE);
				bar.stop();
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
		
				bar.setVisibility(View.GONE);
				bar.stop();

				qcms = FilterManager.getFirstFilterData(result);

				updataFirstFilterData();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	protected void updataFirstFilterData() {
		FirstDapter.setDatas(qcms);
	}

	// ��һ����Ŀ������
	private Qcm qcm = null;
	// ���Ƶ�һ��ҳ��
	private LinearLayout mLlFirstController;
	// �����һ����Ŀ��λ��
	private int position = -1;

	private MyLoadingBar bar;

	private void initFirstView(View view) {
		btnBack = (TextView) view.findViewById(R.id.btn_back_1);
		btnOk = (TextView) view.findViewById(R.id.btn_ok_1);
		btnBack.setOnClickListener(new TitleOnlck());
		btnOk.setOnClickListener(new TitleOnlck());

		btnCancel = (TextView) view.findViewById(R.id.btn_clear);
		btnCancel.setOnClickListener(new ClearOnlck());

		mRecyclerView = (XRecyclerView) view.findViewById(R.id.seconed_recycleview);
		// ���ò��ֹ�����
		LinearLayoutManager layout = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(layout);
		// ��ӷָ���
		mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
		// ����adapter
		FirstDapter = new FirstFilterAdapter<Qcm>(context);
		mRecyclerView.setAdapter(FirstDapter);

		mRecyclerView.setPullRefreshEnabled(false);
		mRecyclerView.setLoadingMoreEnabled(false);

		// ��Ŀ����¼� һ������
		FirstDapter.setItemClickListener(new OnRecycleViewItemClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public <T> void onItemClick(View view, int position, T t) {

				PopMallMenuFilter.this.position = position;
				qcm = (Qcm) t;

				mTxtCategoryName.setText(qcm.getQn());

				ftvms = maps.get(PopMallMenuFilter.this.position);

				if (!Utils.isStringEmpty(ftvms)) {
					seconedAdapter.setDatas(ftvms);
					firstInSeconedOut();
					showHasDdata();
				} else {
					seconedAdapter.setDatas(null);
					loadSecondeData();
					firstInSeconedOut();
				}
			}
		});
		mLlFirstController = (LinearLayout) view.findViewById(R.id.ll_first_layout);

		bar = (MyLoadingBar) view.findViewById(R.id.load_bar);
	}

	/**
	 * ��һ���������� �ڶ�����ʾ
	 */
	protected void firstInSeconedOut() {

		Animation animationIn = AnimationUtils.loadAnimation(context, R.anim.anim_pop_right_to_left_out);
		mLlSeconedController.setAnimation(animationIn);
		mLlSeconedController.setVisibility(View.VISIBLE);

		Animation animationOut = AnimationUtils.loadAnimation(context, R.anim.anim_pop_right_to_left_in);
		mLlFirstController.setAnimation(animationOut);
		mLlFirstController.setVisibility(View.GONE);
	}

	/**
	 * һ��
	 * 
	 * @author Administrator
	 *
	 */
	private class TitleOnlck implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.btn_back_1) {
				dismiss();
			} else if (id == R.id.btn_ok_1) {// ȷ��
				if (filterItemOnclick != null) {
					filterItemOnclick.onFilterItemOnclickLister(qcms);
				}

				dismiss();
			}
		}

	}

	/**
	 * ���ȫ��
	 * 
	 * @author Administrator
	 *
	 */
	private class ClearOnlck implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.btn_clear) {
				maps.clear();
				qcms = getClearData();
				FirstDapter.setDatas(qcms);
			}
		}

	}

	public List<Qcm> getClearData() {
		List<Qcm> qList = new ArrayList<Qcm>();
		for (Qcm qcm : qcms) {
			qcm.setSubName("ȫ��");
			qList.add(qcm);
		}
		return qList;
	}

	private XRecyclerView mSeconedRcView = null;
	private SeconedFilterAdapter<Ftvm> seconedAdapter = null;

	// ���Ƶڶ���ҳ��
	private LinearLayout mLlSeconedController;

	// ���ذ�ť ȡ��
	private TextView mBtnCancel;
	// ��������
	private TextView mTxtCategoryName;
	// ȷ����ť
	private TextView mBtnSure;

	// û����
	private TextView mTxtNoData;

	private Ftvm ftvm = null;

	private MyLoadingBar loadingBar;

	/**
	 * ��ʼ���ڶ�������
	 * 
	 * @param view
	 */
	private void initSeconedView(View view) {

		mBtnCancel = (TextView) view.findViewById(R.id.btn_cancel);
		mTxtCategoryName = (TextView) view.findViewById(R.id.txt_category_name);
		mBtnSure = (TextView) view.findViewById(R.id.btn_sure);

		mBtnCancel.setOnClickListener(new SeconedFilterClick());
		mBtnSure.setOnClickListener(new SeconedFilterClick());

		mSeconedRcView = (XRecyclerView) view.findViewById(R.id.recycleview);
		// ���ò��ֹ�����
		LinearLayoutManager layout = new LinearLayoutManager(context);
		mSeconedRcView.setLayoutManager(layout);
		// ��ӷָ���
		mSeconedRcView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
		// ����adapter
		seconedAdapter = new SeconedFilterAdapter<Ftvm>(context);
		mSeconedRcView.setAdapter(seconedAdapter);

		mSeconedRcView.setPullRefreshEnabled(false);
		mSeconedRcView.setLoadingMoreEnabled(false);

		// ��Ŀ����¼� һ������
		seconedAdapter.setItemClickListener(new OnRecycleViewItemClickListener() {

			@Override
			public <T> void onItemClick(View view, int position, T t) {
				ftvm = (Ftvm) t;

				// ����ĸ���ѡ��
				flagSelected(position, ftvm);

			}
		});

		mLlSeconedController = (LinearLayout) view.findViewById(R.id.ll_seconed_layout);
		mTxtNoData = (TextView) view.findViewById(R.id.no_data);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.loading);
	}

	/**
	 * ���ѡ�е�
	 * 
	 * @param position
	 * @param ftvm
	 */
	@SuppressWarnings("unchecked")
	protected void flagSelected(int position, Ftvm ftvm) {

		// ȫ����Ϊδѡ��
		List<Ftvm> fs = setAllNotSelcted();

		ftvm.setSelected(true);
		fs.remove(position);
		fs.add(position, ftvm);

		maps.put(PopMallMenuFilter.this.position, fs);
		seconedAdapter.setDatas(fs);
	}

	/**
	 * ȫ����Ϊδѡ��
	 * 
	 * @param position
	 */
	private List<Ftvm> setAllNotSelcted() {

		List<Ftvm> list = new ArrayList<Ftvm>();

		if (!Utils.isStringEmpty(ftvms)) {
			for (int i = 0; i < ftvms.size(); i++) {
				Ftvm ftvm = ftvms.get(i);
				ftvm.setSelected(false);
				list.add(ftvm);
			}
		}

		return list;
	}

	// �����˵�����
	private List<Ftvm> ftvms = null;

	/**
	 * ���صڶ�ҳ����
	 */
	protected void loadSecondeData() {

		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("������,���Ժ�...");
		loadingBar.start();

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(FilterNetUrl.getFilfterSeconedUrl(qcm.getQp()));
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long curByteNum, long totalByteNum) {
				
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				((BaseActivity) context).displayToast(arg2);
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
		
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();

				ftvms = FilterManager.getSeconedFilterData(result, qcm.getQn());
				maps.put(position, ftvms);

				updataSeconedFilterData();

			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	@SuppressWarnings("unchecked")
	protected void updataSeconedFilterData() {
		seconedAdapter.setDatas(ftvms);

		showHasDdata();
	}

	private void showHasDdata() {
		if (Utils.isStringEmpty(ftvms)) {
			mTxtNoData.setVisibility(View.VISIBLE);
		} else {
			mTxtNoData.setVisibility(View.GONE);
		}
	}

	/**
	 * ��������
	 * 
	 * @author Administrator
	 *
	 */
	private class SeconedFilterClick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_cancel:// ȡ��
				cancel();
				break;
			case R.id.btn_sure:// ȷ��

				Ftvm ft = getSelectedFtvm();

				if (ft == null) {
					((BaseActivity) context).displayToast("��δѡ��" + qcm.getQn());
					return;
				}

				if (ft != null) {
					qcms.remove(position);
					qcm.setSubName(ft.getDs());
					qcm.setBd(ft.getBd());
					qcms.add(position, qcm);
					FirstDapter.setDatas(qcms);

				}
				cancel();

				break;

			default:
				break;
			}
		}

	}

	public Ftvm getSelectedFtvm() {

		for (Ftvm ftvm : ftvms) {

			if (ftvm.isSelected()) {

				return ftvm;

			}

		}

		return null;
	}

	private FilterItemOnclick filterItemOnclick;

	public void setFilterItemOnclick(FilterItemOnclick filterItemOnclick) {
		this.filterItemOnclick = filterItemOnclick;
	}

	public interface FilterItemOnclick {
		public void onFilterItemOnclickLister(List<Qcm> qcms);
	}

	/**
	 * ��������ȡ��
	 */
	public void cancel() {

		Animation animationOut = AnimationUtils.loadAnimation(context, R.anim.anim_pop_left_to_right_in);
		mLlFirstController.setAnimation(animationOut);
		mLlFirstController.setVisibility(View.VISIBLE);

		Animation animationIn = AnimationUtils.loadAnimation(context, R.anim.anim_pop_left_to_right_out);
		mLlSeconedController.setAnimation(animationIn);
		mLlSeconedController.setVisibility(View.GONE);

	}

}
