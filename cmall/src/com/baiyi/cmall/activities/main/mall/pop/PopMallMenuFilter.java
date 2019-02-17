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
 * 商城-筛选Pop
 * 
 * @author tangkun
 *
 */
public class PopMallMenuFilter extends BasePopupWindow {

	private Context context = null;

	// 一级
	private TextView btnBack = null;
	private TextView btnOk = null;
	private TextView btnCancel = null;

	// 列表数据
	private XRecyclerView mRecyclerView = null;
	// 一级分类适配器
	private FirstFilterAdapter<Qcm> FirstDapter = null;
	// 一级分类数据
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
	 * 加载数据 http://u1q4567516.iask.in/cmallwebservice/Index/Mall/Fif
	 */
	private void loadData() {

		bar.setVisibility(View.VISIBLE);
		bar.setProgressInfo("加载中,请稍后...");
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

	// 第一个条目的数据
	private Qcm qcm = null;
	// 控制第一个页面
	private LinearLayout mLlFirstController;
	// 点击第一个条目的位置
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
		// 设置布局管理器
		LinearLayoutManager layout = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(layout);
		// 添加分割线
		mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
		// 设置adapter
		FirstDapter = new FirstFilterAdapter<Qcm>(context);
		mRecyclerView.setAdapter(FirstDapter);

		mRecyclerView.setPullRefreshEnabled(false);
		mRecyclerView.setLoadingMoreEnabled(false);

		// 条目点击事件 一级分类
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
	 * 第一个界面隐藏 第二个显示
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
	 * 一级
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
			} else if (id == R.id.btn_ok_1) {// 确定
				if (filterItemOnclick != null) {
					filterItemOnclick.onFilterItemOnclickLister(qcms);
				}

				dismiss();
			}
		}

	}

	/**
	 * 清楚全部
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
			qcm.setSubName("全部");
			qList.add(qcm);
		}
		return qList;
	}

	private XRecyclerView mSeconedRcView = null;
	private SeconedFilterAdapter<Ftvm> seconedAdapter = null;

	// 控制第二个页面
	private LinearLayout mLlSeconedController;

	// 返回按钮 取消
	private TextView mBtnCancel;
	// 分类名称
	private TextView mTxtCategoryName;
	// 确定按钮
	private TextView mBtnSure;

	// 没数据
	private TextView mTxtNoData;

	private Ftvm ftvm = null;

	private MyLoadingBar loadingBar;

	/**
	 * 初始化第二个界面
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
		// 设置布局管理器
		LinearLayoutManager layout = new LinearLayoutManager(context);
		mSeconedRcView.setLayoutManager(layout);
		// 添加分割线
		mSeconedRcView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
		// 设置adapter
		seconedAdapter = new SeconedFilterAdapter<Ftvm>(context);
		mSeconedRcView.setAdapter(seconedAdapter);

		mSeconedRcView.setPullRefreshEnabled(false);
		mSeconedRcView.setLoadingMoreEnabled(false);

		// 条目点击事件 一级分类
		seconedAdapter.setItemClickListener(new OnRecycleViewItemClickListener() {

			@Override
			public <T> void onItemClick(View view, int position, T t) {
				ftvm = (Ftvm) t;

				// 标记哪个被选中
				flagSelected(position, ftvm);

			}
		});

		mLlSeconedController = (LinearLayout) view.findViewById(R.id.ll_seconed_layout);
		mTxtNoData = (TextView) view.findViewById(R.id.no_data);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.loading);
	}

	/**
	 * 标记选中的
	 * 
	 * @param position
	 * @param ftvm
	 */
	@SuppressWarnings("unchecked")
	protected void flagSelected(int position, Ftvm ftvm) {

		// 全部置为未选中
		List<Ftvm> fs = setAllNotSelcted();

		ftvm.setSelected(true);
		fs.remove(position);
		fs.add(position, ftvm);

		maps.put(PopMallMenuFilter.this.position, fs);
		seconedAdapter.setDatas(fs);
	}

	/**
	 * 全部置为未选中
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

	// 二级菜单数据
	private List<Ftvm> ftvms = null;

	/**
	 * 加载第二页数据
	 */
	protected void loadSecondeData() {

		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("加载中,请稍后...");
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
	 * 二级分类
	 * 
	 * @author Administrator
	 *
	 */
	private class SeconedFilterClick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_cancel:// 取消
				cancel();
				break;
			case R.id.btn_sure:// 确定

				Ftvm ft = getSelectedFtvm();

				if (ft == null) {
					((BaseActivity) context).displayToast("尚未选择" + qcm.getQn());
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
	 * 二级标题取消
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
