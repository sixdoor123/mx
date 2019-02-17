/**
 * 
 */
package com.baiyi.cmall.activities.main.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.business.adapter.FeedBackAdapter;
import com.baiyi.cmall.activities.main.business.adapter.HorizontalListViewAdapter;
import com.baiyi.cmall.activities.main.business.entity.FeedBackEntity;
import com.baiyi.cmall.activities.main.business.help.FileUtil;
import com.baiyi.cmall.activities.main.business.help.HorizontalListView;
import com.baiyi.cmall.utils.ImageTools;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * @author tangkun
 *
 */
public class FeedBackShopActivity extends BaseActivity{
	
	// 消息流
	private RecyclerView listView = null;
	private FeedBackAdapter fAdapter = null;
	
	/**
	 *  发送文字消息
	 */
	private EditText edtFeed = null;
	private Button btnSend = null;

	/**
	 *  发送图片
	 */
	private ImageView imgAddImg = null;
	private LinearLayout linAddImg = null;
	/**
	 *  图片list
	 */
	private HorizontalListView imgListView = null;
	private HorizontalListViewAdapter imgAdapter = null;
	
	/**
	 *  发送
	 */
	private Button btnGallary = null; // 去相册
	private Button btnSendImg = null; // 
	private CheckBox chbYuan = null;
	
	/* (non-Javadoc)
	 * @see com.baiyi.cmall.activities.main.BaseActivity#initWin(boolean)
	 */
	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);
		ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_feedback,
				win_content);
		initView();
		initList();
		initHorizList();
		initData();
	}
	
	private void initList() {
		listView = (XRecyclerView) findViewById(R.id.feedback_listview);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		listView.setLayoutManager(llm);
//		listView.SetFooterCanUse(false);
		
	}
	
	
	private void initData() {
		ArrayList<FeedBackEntity> list = new ArrayList<FeedBackEntity>();
		
		FeedBackEntity entity = new FeedBackEntity();
		entity.setAnswer("欢迎反馈，请在这里写下你的意见，我们真诚盼望，感激有加，并保证会采取有力行动予以响应。");
		entity.setReply_time(Utils.getCurrentTime2(System.currentTimeMillis()));
		entity.setUserType(FeedBackAdapter.Type_System);
		list.add(entity);
		fAdapter = new FeedBackAdapter(this, list);
		listView.setAdapter(fAdapter);
//		fAdapter.setDatas(list);
//		loadAddFeed(entity);
		
	}
	
	
	public Map<String, String> map = new HashMap<String, String>();
	private List<byte[]> bitmaps = new ArrayList<byte[]>();
	private void initHorizList() {
		imgListView = (HorizontalListView) findViewById(R.id.list_img);
		imgAdapter = new HorizontalListViewAdapter(this);
		imgListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				
				
				CheckBox chb = (CheckBox)view.findViewById(R.id.chb_photo);
				
				if (!chb.isChecked() && !Utils.isStringEmpty(getSelectImgList())&& getSelectImgList().size()>=3) {
					displayToast("最多发三张");
					return;
				}
				
				if (chb.isChecked()) {
					chb.setChecked(false);
					map.remove(imgAdapter.getDatas().get(position));
					imgAdapter.removeSelectPath(imgAdapter.getDatas().get(position));
				}else {
					chb.setChecked(true);
					map.put(imgAdapter.getDatas().get(position),imgAdapter.getDatas().get(position));
					imgAdapter.addSelectPath(imgAdapter.getDatas().get(position));
				}
			}
			
		});
		imgListView.setAdapter(imgAdapter);
	}
	
	private void initView() {
		edtFeed = (EditText) findViewById(R.id.edt_feed);
		edtFeed.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					linAddImg.setVisibility(View.GONE);
					imgAddImg.setSelected(false);
				}
				
			}
		});
		btnSend = (Button) findViewById(R.id.btn_send);
		btnSend.setOnClickListener(new OnClick());
		imgAddImg = (ImageView) findViewById(R.id.img_write);
		linAddImg = (LinearLayout) findViewById(R.id.lin_addimg);
		imgAddImg.setOnClickListener(new OnClick());
		btnSendImg = (Button) findViewById(R.id.btn_sendimg);
		btnSendImg.setOnClickListener(new OnClick());
		
		
//		loadFeedList();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		loadData();
	}
	
	
	
	class OnClick implements OnClickListener{

		@Override
		public void onClick(View v) {
//			if (v.getId() == R.id.btn_send) {
//				
//				sendTextFeed();
//				
//			}else 
				if (v.getId() == R.id.img_write) {
				if (imgAddImg.isSelected()) {
					linAddImg.setVisibility(View.GONE);
					imgAddImg.setSelected(false);
//					edtFeed.setFocusable(true);
					
				}else {
					
					edtFeed.clearFocus();
					inShowInput(false);
					
					if (imgAdapter.getCount() == 0) {
//						String path = ContextUtil.getSDPath()+"/Pictures/Screenshots/";
						List<String> paths = FileUtil.getScreenImageFolder(FeedBackShopActivity.this);
						imgAdapter.setDatas(FileUtil.getImageList(paths));
					}
					
					linAddImg.setVisibility(View.VISIBLE);
					imgAddImg.setSelected(true);
					imgListView.findFocus();
					
//					edtFeed.setFocusable(false);
				}
			}else if (v.getId() == R.id.btn_sendimg || v.getId() == R.id.btn_send) {
				if (Utils.isStringEmpty(edtFeed.getText().toString().trim())) {
					displayToast(getResources().getString(R.string.tip_feedback_null));
					return;
				}
				List<String> list = getSelectImgList();
				if (Utils.isStringEmpty(list)) {
					sendTextFeed();
					return;
				}
				FeedBackEntity entity = new FeedBackEntity();
				entity.setImgList(list);
				entity.setQuestion(edtFeed.getText().toString());
				entity.setCreated_at(Utils.getCurrentTime2(System.currentTimeMillis()));
				
				if (Utils.isStringEmpty(entity.getQuestion())) {
					entity.setUserType(FeedBackAdapter.Type_User_Img);
				}else {
					entity.setUserType(FeedBackAdapter.Type_User);
				}
				edtFeed.setText("");
				addFeedback(entity);
				
				fAdapter.addItem(entity);
				ScrlltoLast();
			}
			
		}
		
	}
	
	private void sendTextFeed() {
		if (Utils.isStringEmpty(edtFeed.getText().toString().trim())) {
			displayToast("不能发表空意见");
			return;
		}
		
		FeedBackEntity entity = new FeedBackEntity();
		entity.setQuestion(edtFeed.getText().toString());
		entity.setCreated_at(Utils.getCurrentTime2(System.currentTimeMillis()));
//		if (Utils.isStringEmpty(entity.getQuestion())) {
//			entity.setUserType(FeedBackAdapter.Type_User_Img);
//		}else {
		entity.setUserType(FeedBackAdapter.Type_User_NoImg);
//		}
		fAdapter.addItem(entity);
		edtFeed.setText("");
		inShowInput(false);
//		loadAddFeed(entity);
		addFeedback(entity);
		
		ScrlltoLast();

	}
	
	/**
	 * 隐藏输入框
	 */
	private void inShowInput(boolean isShow) {
		InputMethodManager imm = (InputMethodManager) edtFeed.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (isShow) {
			imm.showSoftInput(edtFeed,InputMethodManager.SHOW_FORCED); 
		}else {
			imm.hideSoftInputFromWindow(edtFeed.getWindowToken(), 0);
		}

	}
	
	   public ArrayList<String> getSelectImgList()
		{
			Iterator iter = map.entrySet().iterator();
			ArrayList<String> dataList = new ArrayList<String>();
			while(iter.hasNext())
			{
				Map.Entry entry = (Map.Entry) iter.next();
				String path = entry.getValue().toString();
				if(!Utils.isStringEmpty(path))
				{
					dataList.add(path);
				}
			}
			return dataList;
		}
	
	private Handler handler = new Handler();
	private void ScrlltoLast() {
		handler.post(new Runnable() {
			@SuppressLint("NewApi")
			@Override
			public void run() {
//				ListView list = (ListView) listView.getRefreshableView();
				listView.smoothScrollToPosition(fAdapter.getItemCount());
				
			}
		});

	}

	
	private void addFeedback(final FeedBackEntity entity) {
//		if (Utils.isStringEmpty(MID)) {
//			return;
//		}
//		JsonLoader loader = new JsonLoader(this);
//		loader.setUrl(NetUrl.getAddFeed(MID));
//		loader.setMethod(BaseNetLoder.Method_Post);
//		loader.setType(BaseNetLoder.POST_FORM);
//		if (!Utils.isStringEmpty(entity.getImgList())) {
//			getBitmapList(entity.getImgList());
//			loader.setFileName("FILES");
//			ArrayList<byte[]> imgDataList = new ArrayList<byte[]>();
//			imgDataList.addAll(bitmaps);
//			loader.setImgList(imgDataList);
//		}
//		
////		if (!Utils.isStringEmpty(entity.getQuestion()) ) {
//		loader.setContentTextList(getPostData(entity.getQuestion()));
////		}
//		loader.setLoaderListener(new LoaderListener() {
//
//			@Override
//			public void onProgress(Object tag, long curByteNum,
//					long totalByteNum) {
//			}
//
//			@Override
//			public void onError(Object tag, int responseCode,
//					String errorMessage) {
//				System.out.println(errorMessage);
//				((BaseActivity)context).displayToast(errorMessage);
//			}
//
//			@Override
//			public void onCompelete(Object tag, Object result) {
//				// TODO Auto-generated method stub
//				JSONArray array = (JSONArray) result;
//				System.out.println(array.toString());
//					try {
//						if (JsonParse_User.getState(array.getJSONObject(0))) {
//							linAddImg.setVisibility(View.GONE);
//							imgAddImg.setSelected(false);
//							map.clear();
//							imgAdapter.clearCheck();
//							
//						}
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
////				loadAddFeed(entity);
//			}
//		});
//		CmsApplication.getDataStratey().startLoader(loader);
		
	}
	
	private ArrayList<String[]> getPostData(String question) {

		
		ArrayList<String[]> textList = new ArrayList<String[]>();
		textList.add(new String[] { "question", question});
		
		return textList;

	}
	
	private void loadData() {
//		if (Utils.isStringEmpty(MID)) {
//			return;
//		}
//		JsonLoader loader = new JsonLoader(this);
//		loader.setUrl(NetUrl.getFeedList(MID, 1));
//		loader.setMethod(BaseNetLoder.Method_Get);
//		loader.setLoaderListener(new LoaderListener() {
//
//			@Override
//			public void onProgress(Object tag, long curByteNum,
//					long totalByteNum) {
//
//			}
//
//			@Override
//			public void onError(Object tag, int responseCode,
//					String errorMessage) {
//
//			}
//
//			@Override
//			public void onCompelete(Object tag, Object result) {
//				// TODO Auto-generated method stub
//				JSONArray array = (JSONArray) result;
//				List<FeedBackEntity> list = JsonParse_User.getFeedList(context, array);
//				
//				if (Utils.isStringEmpty(list)) {
//					return;
//				}
//				fAdapter.addData(list);
//				ScrlltoLast();
//			}
//		});
//		CmsApplication.getDataStratey().startLoader(loader);
	}
	
	
//	private void getBitmapList(List<String> imgList) {
//		// TODO Auto-generated method stub
//		for (int i = 0; i < imgList.size(); i++) {
//			new LoadImag().doInBackground(imgList.get(i));
//		}
//		
//		
//	}
	
//	class LoadImag extends AsyncTask<String, Object, Bitmap> {
//
//		protected Bitmap doInBackground(String... params) {
//			String path = params[0];
//			Bitmap bm;
//			try {
//				bm = ImageUtils.revitionImageSize(path);
//				bitmaps.add(ImageTools.getBitmapByte(bm));
//				return bm;
//			} catch (IOException e) {
//				return null;
//			}
//		
//		}
//
//		protected void onPostExecute(Bitmap result) {
//			if(result != null)
//			{
//				
//			}
//			super.onPostExecute(result);
//		}
//
//	}
	
	
	/**
	 *  获取本地列表
	 */
//	private void loadFeedList() {
//		FeedbackLoader loader = new FeedbackLoader();
//		loader.setSelect(null);
//		loader.setLoaderListener(new LoaderListener() {
//			
//			public void onProgress(Object arg0, long arg1, long arg2) {
//				
//			}
//			
//			public void onError(Object arg0, int arg1, String arg2) {
//				initData();
//			}
//			
//			public void onCompelete(Object arg0, Object arg1) {
//				List<FeedBackEntity> entities = (List<FeedBackEntity>) arg1;
//				if (Utils.isStringEmpty(entities)) {
//					initData();
//					return;
//				}
//				
//				fAdapter.setData(entities);
//				
//			}
//		});
//		CmsApplication.getDataStratey().startLoader(loader);
//	}
	
	/**
	 *  添加一条
	 */
//	private void loadAddFeed(FeedBackEntity entity) {
//		
//		FeedbackLoader loader = new FeedbackLoader();
//		loader.setInsert(entity);
//		loader.setLoaderListener(new LoaderListener() {
//
//			@Override
//			public void onProgress(Object arg0, long arg1, long arg2) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onError(Object arg0, int arg1, String arg2) {
//				// TODO Auto-generated method stub
//			}
//
//			@Override
//			public void onCompelete(Object arg0, Object arg1) {
//			}
//		});
//		CmsApplication.getDataStratey().startLoader(loader);
//	}
}
