package com.baiyi.jj.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.utils.TLog;

/**
 * 
 * @author tangkun
 * 
 */
public class PullDownCommentView extends ListView {

	private View mFooterView = null;

	private OnRefreshListener mOnRefreshListener = null; // ��listview����ˢ��ʱ�Ļص�

	private boolean mIsFooterCanUse = true;

	public PullDownCommentView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public PullDownCommentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

	}
	public PullDownCommentView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * ȥ������ˢ�ĺͻ�ø����item
	 */
	public void notifyRefreshComplete() {
		this.showFoot(false);
	}

	/**
	 * ����ˢ�º� ��ȡ����� �ص��¼�
	 * 
	 * @param onRefreshListener
	 */
	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		mOnRefreshListener = onRefreshListener;
	}

	/**
	 * �����Ƿ�֧�ֻ�ȡ����
	 * 
	 * @param isCanUse
	 */
	public void SetFooterCanUse(boolean isCanUse) {
		mIsFooterCanUse = isCanUse;

		if (mIsFooterCanUse) {
			this.setOnScrollListener(scroll);
		} else {
			this.setOnScrollListener(null);
		}
	}

	/**
	 * �ؼ��ĳ�ʼ������
	 */
	public void init() {
		initControls();

		/**
		 * ���ø���Ч��
		 */
		View view = ((LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_item,
				null);
		mFooterView = view;
		addFooterView(view);
		this.showFoot(false);
	}

	/**
	 * list�Ĺ�����������,Ϊ�˼�����list ��������ײ���ʱ����ʾ���ظ���
	 */
	private OnScrollListener scroll = new OnScrollListener() {
		private int mLastVisiableItem = 0;

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (!(PullDownCommentView.this.isShowFooterView())
					&& (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
					&& mLastVisiableItem >= PullDownCommentView.this
							.getAdapter().getCount()) {
				if (mIsFooterCanUse) {
					PullDownCommentView.this.showFoot(true);
					if (mOnRefreshListener != null) {
						mOnRefreshListener.onRefresh();
					}
				}
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			mLastVisiableItem = firstVisibleItem + visibleItemCount;
		}
	};

	/**
	 * ��ʼ��ҳ��ؼ�
	 */
	private void initControls() {
		setFastScrollEnabled(false);
		setDividerHeight(0);

		setFadingEdgeLength(0);
		setCacheColorHint(0x00000000);
	}

	/**
	 * �Ƿ�������ʾ"��ȡ����"
	 * 
	 * @return
	 */
	private boolean isShowFooterView() {

		if (mFooterView != null
				&& mFooterView.findViewById(R.id.refresh_progress)
						.getVisibility() == View.VISIBLE
				&& mFooterView.findViewById(R.id.textView_obtain_more)
						.getVisibility() == View.VISIBLE) {
			return true;
		}
		return false;
	}

	/**
	 * ��ʾ����ȡ����ʾ"��ȡ����"
	 * 
	 * @param show
	 */
	private void showFoot(boolean show) {
		if (null != mFooterView) {
			if (show) {

				mFooterView.findViewById(R.id.refresh_progress).setVisibility(
						View.VISIBLE);
				mFooterView.findViewById(R.id.textView_obtain_more)
						.setVisibility(View.VISIBLE);

				postDelayed(new Runnable() {
					@Override
					public void run() {
						if (PullDownCommentView.this.getCount() > 0) {
							PullDownCommentView.this.setSelection(PullDownCommentView.this
									.getCount() - 1);
						}
					}
				}, 0);

			} else {
				mFooterView.findViewById(R.id.refresh_progress).setVisibility(
						View.GONE);
				mFooterView.findViewById(R.id.textView_obtain_more)
						.setVisibility(View.GONE);
			}
		}
	}

	public interface OnRefreshListener {

		public void onRefresh();
	}

}
