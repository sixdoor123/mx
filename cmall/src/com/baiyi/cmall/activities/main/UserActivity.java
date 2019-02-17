package com.baiyi.cmall.activities.main;

import org.json.JSONArray;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.main.buy.ShoppingCarActivity;
import com.baiyi.cmall.activities.msg.MyMsgListActivity;
import com.baiyi.cmall.activities.user.attention.merchant.MerchantAttentionActivity;
import com.baiyi.cmall.activities.user.buyer.MyPurchaseOrderActivity;
import com.baiyi.cmall.activities.user.buyer.form.MyPurOrderActivity;
import com.baiyi.cmall.activities.user.buyer.intention.AlreadySendPurchaseOrderActivity;
import com.baiyi.cmall.activities.user.delegation.DelegationLogisticsActivity;
import com.baiyi.cmall.activities.user.delegation.DelegationProviderActivity;
import com.baiyi.cmall.activities.user.delegation.DelegationPurchaseActivity;
import com.baiyi.cmall.activities.user.help.HelperCenterActivity;
import com.baiyi.cmall.activities.user.login.CompanyLoginActivity;
import com.baiyi.cmall.activities.user.login.MemberLoginActivity;
import com.baiyi.cmall.activities.user.login.web.MemberCenterWebActivity;
import com.baiyi.cmall.activities.user.login.web.MerchantEntryActivity;
import com.baiyi.cmall.activities.user.login.web.PhoneRegisterActivity;
import com.baiyi.cmall.activities.user.merchant.intention.ProviderIntentationOrderListActivity;
import com.baiyi.cmall.activities.user.merchant.order.MerchantOrderActivity;
import com.baiyi.cmall.activities.user.merchant.product.MyProductActivity;
import com.baiyi.cmall.activities.user.merchant.provider.MyProviderListActivity;
import com.baiyi.cmall.activities.user.other.OtherActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.dialog.MsgBoxNoticeE.MsgBoxEOnClickListener;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.main.cache.CatchUtils;
import com.baiyi.cmall.request.manager.ImageUploadManager;
import com.baiyi.cmall.utils.ImageTools;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.UserIconSelecteUtils;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.imgcache.ImageCache;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.main.UserActivity;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.entity.AddressEntity;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.activities.user.login.web.MerchantCenterActivity;
import com.ycl.chooseavatar.library.OnChoosePictureListener;
import com.ycl.chooseavatar.library.YCLTools;

/**
 * 我的主页 登录
 * 
 * @author tangkun
 * 
 */
@SuppressLint("InflateParams")
public class UserActivity extends BaseMsgActivity implements OnClickListener,
		OnChoosePictureListener {

	// 用户名
	private String userName;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(true);
		initTitle();
		initHead();
		// 我是采购商
		initMyPurchaseContent();
		// 我是供应商
		initMyProviderContent();
		// 委托
		initMyDelegateContent();
		// 收藏/关注
		initMyCollectContent();

		initFoot();
		YCLTools.getInstance().setOnChoosePictureListener(this);
	}

	/**
	 * 标题
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("我的");
		topTitleView.mImgBack.setVisibility(View.INVISIBLE);
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				topTitleView.displayPoP(MsgItemUtil.Pop_Main_Txt,
						MsgItemUtil.Pop_Main_Img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickMainPopItem(state, UserActivity.this);
			}
		});
		topTitleView.setNewsLayoutVisible(View.VISIBLE);
		win_title.addView(topTitleView);
	}

	// 我的消息条目
	private LinearLayout mLlMyInformationItem;
	// 我的消息的数量
	private TextView mTxtInformationNumber;
	// 其他的条目
	private LinearLayout mLlOtherItem;
	// 帮助中心的条目
	private LinearLayout mLlHelperItem;

	/**
	 * 我的消息、帮助中心、其他
	 */
	@SuppressLint("InflateParams")
	private void initFoot() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_main_login_foot, null);
		win_content.addView(view);
		mLlMyInformationItem = (LinearLayout) view
				.findViewById(R.id.ll_my_information_item);
		mLlMyInformationItem.setOnClickListener(this);
		mTxtInformationNumber = (TextView) view
				.findViewById(R.id.txt_information_number);
		mTxtInformationNumber.setText(String.format(
				getString(R.string.main_title_num), 8));
		mLlOtherItem = (LinearLayout) view.findViewById(R.id.ll_other_item);
		mLlOtherItem.setOnClickListener(this);
		mLlHelperItem = (LinearLayout) view.findViewById(R.id.ll_help_item);
		mLlHelperItem.setOnClickListener(this);
	}

	// 用户头像
	private ImageView mImgUserIcon;
	// 用户昵称
	private TextView mTxtUserNickName;

	// 选择投降的工具类
	@SuppressWarnings("unused")
	private UserIconSelecteUtils utils;

	// 显示公司名称 注册按钮
	private TextView mTxtRegister;

	/**
	 * 初始化 头像、积分、余额、昵称等信息
	 */
	@SuppressLint("InflateParams")
	private void initHead() {

		utils = UserIconSelecteUtils.getInsetence(this);

		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.item_main_login, null);
		win_content.addView(view);

		mImgUserIcon = (ImageView) view.findViewById(R.id.img_user_icon);
		mImgUserIcon.setOnClickListener(this);
		// mImgUserIcon.setImageBitmap(ImageTools
		// .resIdToBitmap(this, R.drawable.pop));

		mTxtUserNickName = (TextView) view.findViewById(R.id.txt_user_nickname);
		userName = XmlUtils.getInstence(this).getXmlStringValue("userName");
		if (TextUtils.isEmpty(userName)) {
			mTxtUserNickName.setText("点击登录");
		} else {
			mTxtUserNickName.setText(userName);
		}
		mTxtUserNickName.setOnClickListener(this);

		// 注册按钮
		mTxtRegister = (TextView) view.findViewById(R.id.txt_register);
		mTxtRegister.setOnClickListener(this);
	}

	// 我的订单
	// 控制子条目显示还是隐藏的线性布局
	private LinearLayout mLlMyCollectMainCotrol;
	// 条目上的百分比数量
	private TextView mTxtAttentionNumber;

	// 买家关注
	// 控制点击买家关注进入下一集页面
	private LinearLayout mLiFirstBuyerColl;
	// 条目标题
	private TextView mTxtBuyerTitle;
	// 商家关注
	// 控制点击商家关注进入下一集页面
	private LinearLayout mLlCompanyColl;
	// 商家关注的标题
	private TextView mTxtCompanyTitle;

	// 控制下面的内容是否显示
	private LinearLayout mLlControlOrShow;
	// 控制箭头详细或者向上
	private ImageView mImgUpOrDown;

	/**
	 * 我的收藏
	 */
	@SuppressLint("InflateParams")
	private void initMyCollectContent() {
		// 我的收藏
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_main_login_collect, null);
		win_content.addView(view);
		mLlMyCollectMainCotrol = (LinearLayout) view
				.findViewById(R.id.ll_main_collection);
		mLlMyCollectMainCotrol.setOnClickListener(this);

		mTxtAttentionNumber = (TextView) view
				.findViewById(R.id.txt_attention_information_number);
		mTxtAttentionNumber.setText(String.format(
				getString(R.string.main_title_num), 12));

		mLiFirstBuyerColl = (LinearLayout) view
				.findViewById(R.id.ll_first_sub_item);
		mLiFirstBuyerColl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 进入买家关注的页面
				if (isLogin()) {
					goActivity(MemberLoginActivity.class);
					return;
				}
				goActivity(com.baiyi.cmall.activities.user.attention.buyer.BuyerAttentionActivity.class);
			}
		});
		mTxtBuyerTitle = (TextView) view.findViewById(R.id.txt_first_sub_title);
		mTxtBuyerTitle.setText("买家关注");

		mLlCompanyColl = (LinearLayout) view
				.findViewById(R.id.ll_seconed_sub_item);
		mLlCompanyColl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 进入商家关注
				if (isLogin()) {
					goActivity(MemberLoginActivity.class);
					return;
				}
				if (!info.isIscompany()) {
					// 不是商家
					RegisterMerchantDialog();
					return;
				}
				goActivity(MerchantAttentionActivity.class);
			}
		});

		mTxtCompanyTitle = (TextView) view
				.findViewById(R.id.txt_seconed_sub_title);
		mTxtCompanyTitle.setText("商家关注");

		mLlControlOrShow = (LinearLayout) view
				.findViewById(R.id.control_collect);
		mImgUpOrDown = (ImageView) view.findViewById(R.id.img_up_down_arrow);
	}

	/**
	 * 弹出提示框让用户选择是否跳转至“供应商入驻”页
	 */
	protected void RegisterMerchantDialog() {
		displayMsgBoxE("提示", "您目前不是供应家,无访问权限,是否进入供应商入驻 ? ",
				new MsgBoxEOnClickListener() {

					@Override
					public void onClickListener() {
						// 进入供应商入驻
						// displayToast("进入供应商入驻界面");
						goActivity(MerchantEntryActivity.class);
					}
				});
	}

	// 我的委托
	// 控制子条目显示还是隐藏的线性布局
	private LinearLayout mLlMyDelegateMainCotrol;
	// 主条目的文字、标题
	private TextView mTxtMyDelegateMainTitle;
	// 主条目的的图标
	private ImageView mImgMyDelegateMainIcon;
	// 主条目的信息数目
	private TextView mTxtDelegateMainNumber;
	// 最后面的向下或者向上箭头的控制
	private ImageView mImgMyDelegateUpDownArrow;
	// 存储显示还是隐藏子条目的线性布局
	private LinearLayout mLlMyDelegateSubAllTitle;
	// 第一个子条目 点击可进入下一个界面
	private LinearLayout mLlDelegateFirstSubItem;
	// 第一个子条目的标题
	private TextView mTxtDelegateFirstSubTitle;
	// 第一个子标题中的我的采购的数量数量
	private TextView mTxtDelegateFirstSubNumber;
	// 第二个子条目 点击可进入下一个界面
	private LinearLayout mLlDelegateSeconedSubItem;
	// 第二个子条目的标题
	private TextView mTxtDelegateSeconedSubTitle;
	// 第二个子标题中的我的采购的数量数量
	private TextView mTxtDelegateThirdSubNumber;
	// 第三个子条目 点击可进入下一个界面
	private LinearLayout mLlDelegateThirdSubItem;
	// 第三个子条目的标题
	private TextView mTxtDelegateThirdSubTitle;
	// 第三个子标题中的我的采购的数量数量
	private TextView mTxtDelegateSeconedSubNumber;
	// 标志位
	private boolean flag2 = true;

	/**
	 * 我的委托
	 */
	@SuppressLint("InflateParams")
	private void initMyDelegateContent() {
		// 我的委托
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_main_login_logistics, null);
		win_content.addView(view);

		mImgMyDelegateUpDownArrow = (ImageView) view
				.findViewById(R.id.img_up_down_arrow);
		mLlMyDelegateMainCotrol = (LinearLayout) view
				.findViewById(R.id.ll_main_cotrol);
		// FIXME 默认是隐藏
		mLlMyDelegateSubAllTitle = (LinearLayout) view
				.findViewById(R.id.ll_sub_all_title_01);
		mLlMyDelegateSubAllTitle.setVisibility(View.GONE);
		mLlMyDelegateMainCotrol.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 控制我的委托下的子条目时显示还是隐藏
				if (flag2) {
					mImgMyDelegateUpDownArrow
							.setImageResource(R.drawable.up_arrow);
					mLlMyDelegateSubAllTitle.setVisibility(View.VISIBLE);
					flag2 = false;
				} else {
					mImgMyDelegateUpDownArrow
							.setImageResource(R.drawable.down_arrow);
					mLlMyDelegateSubAllTitle.setVisibility(View.GONE);
					flag2 = true;
				}
			}
		});
		mTxtMyDelegateMainTitle = (TextView) view
				.findViewById(R.id.txt_main_title);
		mTxtMyDelegateMainTitle.setText("我的委托");
		mImgMyDelegateMainIcon = (ImageView) view
				.findViewById(R.id.img_main_icon);

		mImgMyDelegateMainIcon.setImageResource(R.drawable.user_entrust);
		mTxtDelegateMainNumber = (TextView) view
				.findViewById(R.id.txt_main_number);
		// 设置我的采购的数量
		mTxtDelegateMainNumber.setText(String.format(
				getString(R.string.main_title_num), 12));

		mLlDelegateFirstSubItem = (LinearLayout) view
				.findViewById(R.id.ll_first_sub_item);
		mLlDelegateFirstSubItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 由委托采购进入下一页
				if (isLogin()) {
					goActivity(MemberLoginActivity.class);
					return;
				} else {
					goActivity(DelegationPurchaseActivity.class);
				}
			}
		});
		mTxtDelegateFirstSubTitle = (TextView) view
				.findViewById(R.id.txt_first_sub_title);
		mTxtDelegateFirstSubTitle.setText("委托采购");
		mTxtDelegateFirstSubNumber = (TextView) view
				.findViewById(R.id.txt_first_sub_number);
		mTxtDelegateFirstSubNumber.setText(String.format(
				getString(R.string.main_title_num), 6));

		mLlDelegateSeconedSubItem = (LinearLayout) view
				.findViewById(R.id.ll_seconed_sub_item);
		mLlDelegateSeconedSubItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 由委托供应进入下一页
				if (isLogin()) {
					goActivity(MemberLoginActivity.class);
					return;
				}
				goActivity(DelegationProviderActivity.class);
			}
		});
		mTxtDelegateSeconedSubTitle = (TextView) view
				.findViewById(R.id.txt_seconed_sub_title);
		mTxtDelegateSeconedSubTitle.setText("委托供应");
		mTxtDelegateSeconedSubNumber = (TextView) view
				.findViewById(R.id.txt_seconed_sub_number);
		mTxtDelegateSeconedSubNumber.setText(String.format(
				getString(R.string.main_title_num), 3));
		// 第三个条目
		mLlDelegateThirdSubItem = (LinearLayout) view
				.findViewById(R.id.ll_third_sub_item);
		mLlDelegateThirdSubItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 由委托物流进入下一页
				if (isLogin()) {
					goActivity(MemberLoginActivity.class);
					return;
				}
				goActivity(DelegationLogisticsActivity.class);
			}
		});
		mTxtDelegateThirdSubTitle = (TextView) view
				.findViewById(R.id.txt_third_sub_title);
		mTxtDelegateThirdSubTitle.setText("委托物流");
		mTxtDelegateThirdSubNumber = (TextView) view
				.findViewById(R.id.txt_third_sub_number);
		mTxtDelegateThirdSubNumber.setText(String.format(
				getString(R.string.main_title_num), 3));
	}

	// 我的供应
	// 控制子条目显示还是隐藏的线性布局
	private LinearLayout mLlMyProviderMainCotrol;
	// 主条目的文字、标题
	private TextView mTxtMyProviderMainTitle;
	// 主条目的的图标
	private ImageView mImgMyProviderMainIcon;
	// 主条目的信息数目
	private TextView mTxtProviderMainNumber;
	// 最后面的向下或者向上箭头的控制
	private ImageView mImgMyProviderUpDownArrow;
	// 存储显示还是隐藏子条目的线性布局
	private LinearLayout mLlMyProviderSubAllTitle;
	// 第一个子条目 点击可进入下一个界面
	private LinearLayout mLlProviderFirstSubItem;
	// 第一个子条目的标题
	private TextView mTxtProviderFirstSubTitle;
	// 第一个子标题中的我的采购的数量数量
	private TextView mTxtProviderFirstSubNumber;
	// 第二个子条目 点击可进入下一个界面
	private LinearLayout mLlProviderSeconedSubItem;
	// 第二个子条目的标题
	private TextView mTxtProviderSeconedSubTitle;
	// 第二个子标题中的我的采购的数量数量
	private TextView mTxtProviderSeconedSubNumber;
	// 第三个子条目 点击可进入下一个界面
	private LinearLayout mLlDelegateThirdSubPro;
	// 第三个子条目的内容
	private TextView mTxtDelegateThirdTitlePro;

	// 标志位
	private boolean flag4 = true;

	// 第四个条目点击可进入下一个界面
	private LinearLayout mLlFourMyProduct;
	// 第四个条目的内容
	private TextView mTxtMyProductTitle;

	/**
	 * 我是供应商条目
	 */
	private void initMyProviderContent() {
		// 我的供应
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_main_login, null);
		win_content.addView(view);
		mImgMyProviderUpDownArrow = (ImageView) view
				.findViewById(R.id.img_up_down_arrow);
		mLlMyProviderMainCotrol = (LinearLayout) view
				.findViewById(R.id.ll_main_cotrol);
		// FIXME 默认是隐藏
		mLlMyProviderSubAllTitle = (LinearLayout) view
				.findViewById(R.id.ll_sub_all_title_01);
		mLlMyProviderSubAllTitle.setVisibility(View.GONE);
		mLlMyProviderMainCotrol.setOnClickListener(new OnClickListener() {

			@SuppressLint("InflateParams")
			@Override
			public void onClick(View v) {
				if (info == null) {
					// displayToast("用户未登录,请登录后重试.");
					goActivity(MemberLoginActivity.class);
					return;
				}
				if (info.isIscompany()) {
					if (flag4) {
						mLlMyProviderSubAllTitle.setVisibility(View.VISIBLE);
						mImgMyProviderUpDownArrow
								.setImageResource(R.drawable.up_arrow);
						flag4 = false;
					} else {
						mLlMyProviderSubAllTitle.setVisibility(View.GONE);
						mImgMyProviderUpDownArrow
								.setImageResource(R.drawable.down_arrow);
						flag4 = true;
					}
				} else {
					EntryCompanyDialog();
				}
			}
		});
		mTxtMyProviderMainTitle = (TextView) view
				.findViewById(R.id.txt_main_title);
		mTxtMyProviderMainTitle.setText("我是供应商");
		mImgMyProviderMainIcon = (ImageView) view
				.findViewById(R.id.img_main_icon);
		mImgMyProviderMainIcon.setImageResource(R.drawable.user_business);
		mTxtProviderMainNumber = (TextView) view
				.findViewById(R.id.txt_main_number);
		// 设置我的采购的数量
		mTxtProviderMainNumber.setText(String.format(
				getString(R.string.main_title_num), 12));

		mLlProviderFirstSubItem = (LinearLayout) view
				.findViewById(R.id.ll_first_sub_item);
		mLlProviderFirstSubItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 我的供应
				// goActivity(MyProviderOrderActivity.class);
				if (isLogin()) {
					goActivity(MemberLoginActivity.class);
					return;
				} else {
					goActivity(MyProviderListActivity.class);
				}
			}
		});
		mTxtProviderFirstSubTitle = (TextView) view
				.findViewById(R.id.txt_first_sub_title);
		mTxtProviderFirstSubTitle.setText("我的供应");
		mTxtProviderFirstSubNumber = (TextView) view
				.findViewById(R.id.txt_first_sub_number);
		mTxtProviderFirstSubNumber.setText(String.format(
				getString(R.string.main_title_num), 6));

		mLlProviderSeconedSubItem = (LinearLayout) view
				.findViewById(R.id.ll_seconed_sub_item);
		mLlProviderSeconedSubItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 由供应意向进入下一页
				goActivity(ProviderIntentationOrderListActivity.class);
			}
		});
		mTxtProviderSeconedSubTitle = (TextView) view
				.findViewById(R.id.txt_seconed_sub_title);
		mTxtProviderSeconedSubTitle.setText("供应意向");
		mTxtProviderSeconedSubNumber = (TextView) view
				.findViewById(R.id.txt_seconed_sub_number);
		mTxtProviderSeconedSubNumber.setText(String.format(
				getString(R.string.main_title_num), 6));

		// 第三个条目
		mLlDelegateThirdSubPro = (LinearLayout) view
				.findViewById(R.id.ll_third_sub_item);
		mLlDelegateThirdSubPro.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 由供应订单进入下一页
				// goActivity(MyProviderOrderFormActivity.class);
				goActivity(MerchantOrderActivity.class);
			}
		});
		mTxtDelegateThirdTitlePro = (TextView) view
				.findViewById(R.id.txt_third_sub_title);
		mTxtDelegateThirdTitlePro.setText("供应订单");

		// 第四个条目 - 我的产品
		mLlFourMyProduct = (LinearLayout) view
				.findViewById(R.id.ll_four_sub_item);
		mLlFourMyProduct.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 进入我的产品
				goActivity(MyProductActivity.class);
			}
		});
		mTxtMyProductTitle = (TextView) view
				.findViewById(R.id.txt_four_sub_title);
		mTxtMyProductTitle.setText("我的产品");
	}

	/**
	 * 弹出提示框让用户选择是否跳转至“供应商入驻”页
	 */
	protected void EntryCompanyDialog() {
		displayMsgBoxE("提示", "是否进入供应商入驻 ? ", new MsgBoxEOnClickListener() {

			@Override
			public void onClickListener() {
				// 进入供应商入驻
				// displayToast("进入供应商入驻界面");
				goActivity(MerchantEntryActivity.class);
			}
		});
	}

	// 我的采购
	// 控制子条目显示还是隐藏的线性布局
	private LinearLayout mLlMyPurchaseMainCotrol;
	// 主条目的文字、标题
	private TextView mTxtMyPurchaseMainTitle;
	// 主条目的的图标
	private ImageView mImgMyPurchaseMainIcon;
	// 主条目的信息数目
	private TextView mTxtMainNumber;
	// 最后面的向下或者向上箭头的控制
	private ImageView mImgMyPurchaseUpDownArrow;
	// 存储显示还是隐藏子条目的线性布局
	private LinearLayout mLlMyPurchaseSubAllTitle;
	// 第一个子条目 点击可进入下一个界面
	private LinearLayout mLlFirstSubItem;
	// 第一个子条目的标题
	private TextView mTxtFirstSubTitle;
	// 第一个子标题中的我的采购的数量数量
	private TextView mTxtFirstSubNumber;
	// 第二个子条目 点击可进入下一个界面
	private LinearLayout mLlSeconedSubItem;
	// 第二个子条目的标题
	private TextView mTxtSeconedSubTitle;
	// 第二个子标题中的我的采购的数量数量
	private TextView mTxtSeconedSubNumber;
	// 第三个子条目 点击可进入下一个界面
	private LinearLayout mLlDelegateThirdSub;
	// 第三个子条目的内容
	private TextView mTxtDelegateThirdTitle;

	// 第四个条目点击可进入下一个界面
	private LinearLayout mLlFourShoopingBus;
	// 第四个条目的内容
	private TextView mTxtFourTitle;

	/**
	 * 主体部分 我的采购条目
	 */
	private void initMyPurchaseContent() {
		// 我的采购
		View myPurchaseView = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_main_login, null);
		win_content.addView(myPurchaseView);
		mImgMyPurchaseUpDownArrow = (ImageView) myPurchaseView
				.findViewById(R.id.img_up_down_arrow);
		// FIXME 默认是隐藏
		mLlMyPurchaseSubAllTitle = (LinearLayout) myPurchaseView
				.findViewById(R.id.ll_sub_all_title_01);
		mLlMyPurchaseSubAllTitle.setVisibility(View.GONE);

		mLlMyPurchaseMainCotrol = (LinearLayout) myPurchaseView
				.findViewById(R.id.ll_main_cotrol);
		mLlMyPurchaseMainCotrol.setOnClickListener(this);
		mTxtMyPurchaseMainTitle = (TextView) myPurchaseView
				.findViewById(R.id.txt_main_title);
		mTxtMyPurchaseMainTitle.setText("我是采购商");
		mImgMyPurchaseMainIcon = (ImageView) myPurchaseView
				.findViewById(R.id.img_main_icon);
		mImgMyPurchaseMainIcon.setImageResource(R.drawable.user_buyers);
		mTxtMainNumber = (TextView) myPurchaseView
				.findViewById(R.id.txt_main_number);
		// 设置我的采购的数量
		mTxtMainNumber.setText(String.format(
				getString(R.string.main_title_num), 10));

		mLlFirstSubItem = (LinearLayout) myPurchaseView
				.findViewById(R.id.ll_first_sub_item);
		mLlFirstSubItem.setOnClickListener(this);
		mTxtFirstSubTitle = (TextView) myPurchaseView
				.findViewById(R.id.txt_first_sub_title);
		mTxtFirstSubTitle.setText("我的采购");
		mTxtFirstSubNumber = (TextView) myPurchaseView
				.findViewById(R.id.txt_first_sub_number);
		mTxtFirstSubNumber.setText(String.format(
				getString(R.string.main_title_num), 5));
		mLlSeconedSubItem = (LinearLayout) myPurchaseView
				.findViewById(R.id.ll_seconed_sub_item);
		mLlSeconedSubItem.setOnClickListener(this);
		mTxtSeconedSubTitle = (TextView) myPurchaseView
				.findViewById(R.id.txt_seconed_sub_title);
		mTxtSeconedSubTitle.setText("采购意向");
		mTxtSeconedSubNumber = (TextView) myPurchaseView
				.findViewById(R.id.txt_seconed_sub_number);
		mTxtSeconedSubNumber.setText(String.format(
				getString(R.string.main_title_num), 5));

		// 第三个条目
		mLlDelegateThirdSub = (LinearLayout) myPurchaseView
				.findViewById(R.id.ll_third_sub_item);
		mLlDelegateThirdSub.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 由采购订单进入下一页
				if (isLogin()) {
					goActivity(MemberLoginActivity.class);
					return;
				} else {
					goActivity(MyPurOrderActivity.class);
				}
			}
		});
		mTxtDelegateThirdTitle = (TextView) myPurchaseView
				.findViewById(R.id.txt_third_sub_title);
		mTxtDelegateThirdTitle.setText("采购订单");

		// 第四个条目 - 我的购物车
		mLlFourShoopingBus = (LinearLayout) myPurchaseView
				.findViewById(R.id.ll_four_sub_item);
		mLlFourShoopingBus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 进入购物车
				if (isLogin()) {
					goActivity(MemberLoginActivity.class);
					return;
				} else {
					goActivity(ShoppingCarActivity.class);
				}
			}
		});
		mTxtFourTitle = (TextView) myPurchaseView
				.findViewById(R.id.txt_four_sub_title);
		mTxtFourTitle.setText("我的购物车");
	}

	// 标志位

	private boolean flag5 = true;
	private boolean flag6 = true;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_user_icon:// 头像选择
			token = XmlUtils.getInstence(this).getXmlStringValue("token");
			if (!isLogin()) {
				fixPhoto(AppNetUrl.getPhotoStyleUrl(), "", 1);
			} else {
				goActivity(MemberLoginActivity.class);
			}
			break;
		case R.id.txt_user_nickname:// 点击进行登录或者进入用户设置界面
			token = XmlUtils.getInstence(this).getXmlStringValue("token");
			if (!TextUtils.isEmpty(token)) {
				goActivity(MemberCenterWebActivity.class);
			} else {
				goActivity(MemberLoginActivity.class);
			}
			break;
		case R.id.ll_main_cotrol:// 控制我的采购下的子条目时显示还是隐藏
			if (flag5) {
				mLlMyPurchaseSubAllTitle.setVisibility(View.VISIBLE);
				mImgMyPurchaseUpDownArrow.setImageResource(R.drawable.up_arrow);
				flag5 = false;
			} else {
				mLlMyPurchaseSubAllTitle.setVisibility(View.GONE);
				mImgMyPurchaseUpDownArrow
						.setImageResource(R.drawable.down_arrow);
				flag5 = true;
			}
			break;
		case R.id.ll_first_sub_item:// 控制我的采购下第一个子条目的点击事件 我的采购
			if (isLogin()) {
				goActivity(MemberLoginActivity.class);
				return;
			} else {
				goActivity(MyPurchaseOrderActivity.class);
			}
			break;
		case R.id.ll_seconed_sub_item:// 控制我的采购下第二个子条目的点击事件 采购意向
			if (isLogin()) {
				goActivity(MemberLoginActivity.class);
				return;
			} else {
				goActivity(AlreadySendPurchaseOrderActivity.class, 0);
			}
			break;
		case R.id.ll_my_information_item:// 我的消息的条目的点击事件
			goActivity(MyMsgListActivity.class);
			break;
		case R.id.ll_other_item:// 其他条目的点击事件
			goActivity(OtherActivity.class);
			break;
		case R.id.ll_help_item:// 帮助条目的点击事件
			goActivity(HelperCenterActivity.class);
			break;
		case R.id.ll_main_collection:// 我的收藏（关注）条目的点击事件

			if (flag6) {
				mLlControlOrShow.setVisibility(View.VISIBLE);
				mImgUpOrDown.setImageResource(R.drawable.up_arrow);
				flag6 = false;
			} else {
				mLlControlOrShow.setVisibility(View.GONE);
				mImgUpOrDown.setImageResource(R.drawable.down_arrow);
				flag6 = true;
			}

			break;
		case R.id.txt_register:// 注册按钮(是用户进入供应商入驻界面)
			if (isLogin()) {
				// 手机注册界面
				goActivity(PhoneRegisterActivity.class);
				return;
			}
			if (!info.isIscompany()) {
				// 进入供应商入驻界面(企业会员注册)
				goActivity(MerchantEntryActivity.class);
			} else {
				// 进入供应商中心(企业会员中心)
				if (isCompanyLogin()) {
					goActivity(CompanyLoginActivity.class);
				} else {
					goActivity(companyToken, MerchantCenterActivity.class);
				}
			}
			break;
		}
	}

	private String companyToken;

	/**
	 * 判断供应商是否登录
	 * 
	 * @return
	 */
	private boolean isCompanyLogin() {
		companyToken = XmlUtils.getInstence(UserActivity.this)
				.getXmlStringValue(XmlName.Company_Token);
		if (TextViewUtil.isStringEmpty(companyToken)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否登录
	 * 
	 * @return
	 */
	private boolean isLogin() {
		String token = XmlUtils.getInstence(UserActivity.this)
				.getXmlStringValue(XmlName.TOKEN);
		if (TextUtils.isEmpty(token) || token == null || "".equals(token)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isExecutiveOnResume = true;
	private Bitmap bitmap;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("onActivityResult~~", requestCode + "----onActivityResult-----"
				+ resultCode);

		if (resultCode == 10) {
			if (data != null) {
				info = (LoginInfo) data.getSerializableExtra("key");
				if (info != null) {
					mTxtUserNickName.setText("采购商中心");
					if (info.isIscompany()) {
						mTxtRegister.setText("供应商中心");
					} else {
						mTxtRegister.setText("供应商入驻");
					}
					LoadHeadImg(info.getHeadPortrait());
				} else {
					notLogin();
				}
			}
			isExecutiveOnResume = false;
		} else if (resultCode == 1) {
			// LoginInfo info = (LoginInfo) data.getSerializableExtra("key");
			// DmallApplication.setUserInfo(info);
			isExecutiveOnResume = true;
		}

		super.onActivityResult(requestCode, resultCode, data);
		YCLTools.getInstance().upLoadImage(requestCode, resultCode, data);
	}

	/**
	 * 加载头像
	 * 
	 * @param headPortrait
	 */
	@SuppressLint("ResourceAsColor")
	private void LoadHeadImg(final String headPortrait) {
		if (TextViewUtil.isStringEmpty(headPortrait)) {
			return;
		}
		BitmapDrawable photo = ImageCache.getInstance().getMemoryCache(
				headPortrait);
		if (null != photo) {
			Log.d("TAG", "---从缓存中获取头像----");
			Bitmap bitmap = ImageTools.drawableToBitmap(photo);
			bitmap = ImageTools.getCicelBitmap(bitmap);
			Drawable dr = ImageTools.bitmapToDrawable(bitmap);
			mImgUserIcon.setBackgroundDrawable(dr);
			return;
		}

		ImageLoader loader = new ImageLoader(this, true);
		loader.setUrl(headPortrait);
		loader.setPostData(NullJsonData.getPostData());
		loader.setMethod(BaseNetLoder.Method_Get);
		loader.addRequestHeader("token", token);
		loader.addRequestHeader("iscompany", iscompany);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				Log.d("TT", "LoadHeadImg------errorMessage");
			}

			@SuppressLint("ResourceAsColor")
			@Override
			public void onCompelete(Object tag, Object result) {
				BitmapDrawable drawable = (BitmapDrawable) result;
				Bitmap bitmap = ImageTools.drawableToBitmap(drawable);
				bitmap = ImageTools.getCicelBitmap(bitmap);
				Drawable dr = ImageTools.bitmapToDrawable(bitmap);
				mImgUserIcon.setBackgroundDrawable(dr);
				ImageCache.getInstance().addBitmapToCache(headPortrait,
						drawable);
				Log.d("TT", "LoadHeadImg------onCompelete");

			}
		});
		CmallApplication.getImageStrategy().startLoader(loader);
	}

	private LoginInfo info;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("TAG", "---onResume----");
		// x当设置头像时候不执行一下代码
		if (!isLogin()) {
			if (!isExecutiveOnResume) {
				return;
			}
			info = CmallApplication.getUserInfo();
			if (info != null) {
				mTxtUserNickName.setText("采购商中心");
				if (info.isIscompany()) {
					mTxtRegister.setText("供应商中心");
				} else {
					mTxtRegister.setText("供应商入驻");
				} 
				Log.d("TAG", "再一次进去后的图片URL："+info.getHeadPortrait());
				LoadHeadImg(info.getHeadPortrait());
			}
		} else {
			notLogin();
		}
	}

	private void notLogin() {
		mTxtUserNickName.setText("登录");
		mTxtRegister.setText("注册");
		mImgUserIcon.setBackgroundResource(R.drawable.icon);
		mLlMyProviderSubAllTitle.setVisibility(View.GONE);
		mImgMyProviderUpDownArrow.setImageResource(R.drawable.down_arrow);
		flag4 = true;
		info = null;
	}

	/*
	 * 获取修改头像地址的实体类
	 */
	private AddressEntity addressEntity = null;
	public static final int GET_IMAGE_ADDRESS = 1;
	public static final int IMAGE_UPLOAD = 2;
	public static final int POST_PHOTO = 3;
	public static final int GET_PICTURE = 4;

	/**
	 * 获取修改头像的路径
	 */
	private void fixPhoto(String url, String postData, final int state) {

		// 修改头像的时候让onResume不执行
		isExecutiveOnResume = false;

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(url);
		jsonLoader.setPostData(postData);
		// 最后获取图片为get请求，其他的都是post请求
		if (state == 4) {
			jsonLoader.setMethod(BaseNetLoder.Method_Get);
		} else {
			jsonLoader.setMethod(BaseNetLoder.Method_Post);
		}
		jsonLoader.setType("application/json");
		jsonLoader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		jsonLoader.addRequestHeader("iscompany", iscompany);
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				displayToast(errorMessage);
				mImgUserIcon.setEnabled(true);
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				JSONArray array = null;
				RequestNetResultInfo resultInfo = null;
				if (4 != state) {

					array = (JSONArray) result;
					// 服务器返回结果
					if (1 == state) {
						resultInfo = JsonParse_User.getResultInfo(array);
					} else {
						resultInfo = JsonParse_User.getResultInfo1(array);
					}
				}

				switch (state) {
				case GET_IMAGE_ADDRESS:// 获取上传图片的地址

					mImgUserIcon.setEnabled(true);
					if (NumEntity.PLEASE_LOG.equals(resultInfo.getMsg())) {
						displayToast(resultInfo.getMsg());
						// 清空重新登录
						XmlUtils.getInstence(UserActivity.this).delete();
						notLogin();
						isExecutiveOnResume = true;
						return;
					} else {

						addressEntity = ImageUploadManager
								.getFixPhotoAddressInfo(array);
						/*
						 * 弹出对话框，选择修改头像的时候，照片的来源
						 */
						new com.baiyi.cmall.views.UpLoadHeadImageDialog(
								UserActivity.this).show();
					}
					break;
				case IMAGE_UPLOAD:// 上传图片之后修改头像
					if (resultInfo.getStatus() > 0) {

						String resultData = ImageUploadManager
								.getImageUpLoadInfo(array);
						fixPhoto(addressEntity.getImageupdate(),
								ImageUploadManager.getFixPhotoPostData(bitmap,
										resultData), POST_PHOTO);
					} else {
						isExecutiveOnResume = true;
						displayToast(resultInfo.getMsg());
					}
					break;
				case POST_PHOTO:// 修改头像成功
					if (resultInfo.getStatus() > 0) {
						fixPhoto(addressEntity.getImageaddress() + "?mid="
								+ CmallApplication.getUserInfo().getUserID(),
								"", GET_PICTURE);
					} else {
						isExecutiveOnResume = true;
						displayToast(resultInfo.getMsg());
					}
					break;
				case GET_PICTURE:// 获取头像地址成功

					if (null != info) {
						isExecutiveOnResume = true;
						if (null != result) {
							displayToast("头像更新成功");
							String pic = result.toString().trim();
							Log.d("TAG", "第一次更新成功后的图片URL："+pic);
							LoadHeadImg(pic);
							CatchUtils.saveXml(UserActivity.this, pic);
							// 头像信息存在缓存中，所以必须清空，下次重新获取
							CatchUtils.updtateCacheLogin(UserActivity.this, pic);
						} else {
							displayToast("头像更新失败");
						}
					}
					break;
				}

			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);
	}

	@Override
	public void OnChoose(String filePath) {
		// TODO Auto-generated method stub
		bitmap = ImageTools.getCicelBitmap(BitmapFactory.decodeFile(filePath));

		// mImgUserIcon.setImageBitmap(bitmap);
		fixPhoto(addressEntity.getImageupload(),
				ImageUploadManager.getImageuploadPostData(bitmap), IMAGE_UPLOAD);

	}

	@Override
	public void OnCancel() {
		// TODO Auto-generated method stub
	}
}
