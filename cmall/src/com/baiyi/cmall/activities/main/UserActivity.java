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
 * �ҵ���ҳ ��¼
 * 
 * @author tangkun
 * 
 */
@SuppressLint("InflateParams")
public class UserActivity extends BaseMsgActivity implements OnClickListener,
		OnChoosePictureListener {

	// �û���
	private String userName;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(true);
		initTitle();
		initHead();
		// ���ǲɹ���
		initMyPurchaseContent();
		// ���ǹ�Ӧ��
		initMyProviderContent();
		// ί��
		initMyDelegateContent();
		// �ղ�/��ע
		initMyCollectContent();

		initFoot();
		YCLTools.getInstance().setOnChoosePictureListener(this);
	}

	/**
	 * ����
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("�ҵ�");
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

	// �ҵ���Ϣ��Ŀ
	private LinearLayout mLlMyInformationItem;
	// �ҵ���Ϣ������
	private TextView mTxtInformationNumber;
	// ��������Ŀ
	private LinearLayout mLlOtherItem;
	// �������ĵ���Ŀ
	private LinearLayout mLlHelperItem;

	/**
	 * �ҵ���Ϣ���������ġ�����
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

	// �û�ͷ��
	private ImageView mImgUserIcon;
	// �û��ǳ�
	private TextView mTxtUserNickName;

	// ѡ��Ͷ���Ĺ�����
	@SuppressWarnings("unused")
	private UserIconSelecteUtils utils;

	// ��ʾ��˾���� ע�ᰴť
	private TextView mTxtRegister;

	/**
	 * ��ʼ�� ͷ�񡢻��֡����ǳƵ���Ϣ
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
			mTxtUserNickName.setText("�����¼");
		} else {
			mTxtUserNickName.setText(userName);
		}
		mTxtUserNickName.setOnClickListener(this);

		// ע�ᰴť
		mTxtRegister = (TextView) view.findViewById(R.id.txt_register);
		mTxtRegister.setOnClickListener(this);
	}

	// �ҵĶ���
	// ��������Ŀ��ʾ�������ص����Բ���
	private LinearLayout mLlMyCollectMainCotrol;
	// ��Ŀ�ϵİٷֱ�����
	private TextView mTxtAttentionNumber;

	// ��ҹ�ע
	// ���Ƶ����ҹ�ע������һ��ҳ��
	private LinearLayout mLiFirstBuyerColl;
	// ��Ŀ����
	private TextView mTxtBuyerTitle;
	// �̼ҹ�ע
	// ���Ƶ���̼ҹ�ע������һ��ҳ��
	private LinearLayout mLlCompanyColl;
	// �̼ҹ�ע�ı���
	private TextView mTxtCompanyTitle;

	// ��������������Ƿ���ʾ
	private LinearLayout mLlControlOrShow;
	// ���Ƽ�ͷ��ϸ��������
	private ImageView mImgUpOrDown;

	/**
	 * �ҵ��ղ�
	 */
	@SuppressLint("InflateParams")
	private void initMyCollectContent() {
		// �ҵ��ղ�
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
				// ������ҹ�ע��ҳ��
				if (isLogin()) {
					goActivity(MemberLoginActivity.class);
					return;
				}
				goActivity(com.baiyi.cmall.activities.user.attention.buyer.BuyerAttentionActivity.class);
			}
		});
		mTxtBuyerTitle = (TextView) view.findViewById(R.id.txt_first_sub_title);
		mTxtBuyerTitle.setText("��ҹ�ע");

		mLlCompanyColl = (LinearLayout) view
				.findViewById(R.id.ll_seconed_sub_item);
		mLlCompanyColl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �����̼ҹ�ע
				if (isLogin()) {
					goActivity(MemberLoginActivity.class);
					return;
				}
				if (!info.isIscompany()) {
					// �����̼�
					RegisterMerchantDialog();
					return;
				}
				goActivity(MerchantAttentionActivity.class);
			}
		});

		mTxtCompanyTitle = (TextView) view
				.findViewById(R.id.txt_seconed_sub_title);
		mTxtCompanyTitle.setText("�̼ҹ�ע");

		mLlControlOrShow = (LinearLayout) view
				.findViewById(R.id.control_collect);
		mImgUpOrDown = (ImageView) view.findViewById(R.id.img_up_down_arrow);
	}

	/**
	 * ������ʾ�����û�ѡ���Ƿ���ת������Ӧ����פ��ҳ
	 */
	protected void RegisterMerchantDialog() {
		displayMsgBoxE("��ʾ", "��Ŀǰ���ǹ�Ӧ��,�޷���Ȩ��,�Ƿ���빩Ӧ����פ ? ",
				new MsgBoxEOnClickListener() {

					@Override
					public void onClickListener() {
						// ���빩Ӧ����פ
						// displayToast("���빩Ӧ����פ����");
						goActivity(MerchantEntryActivity.class);
					}
				});
	}

	// �ҵ�ί��
	// ��������Ŀ��ʾ�������ص����Բ���
	private LinearLayout mLlMyDelegateMainCotrol;
	// ����Ŀ�����֡�����
	private TextView mTxtMyDelegateMainTitle;
	// ����Ŀ�ĵ�ͼ��
	private ImageView mImgMyDelegateMainIcon;
	// ����Ŀ����Ϣ��Ŀ
	private TextView mTxtDelegateMainNumber;
	// ���������»������ϼ�ͷ�Ŀ���
	private ImageView mImgMyDelegateUpDownArrow;
	// �洢��ʾ������������Ŀ�����Բ���
	private LinearLayout mLlMyDelegateSubAllTitle;
	// ��һ������Ŀ ����ɽ�����һ������
	private LinearLayout mLlDelegateFirstSubItem;
	// ��һ������Ŀ�ı���
	private TextView mTxtDelegateFirstSubTitle;
	// ��һ���ӱ����е��ҵĲɹ�����������
	private TextView mTxtDelegateFirstSubNumber;
	// �ڶ�������Ŀ ����ɽ�����һ������
	private LinearLayout mLlDelegateSeconedSubItem;
	// �ڶ�������Ŀ�ı���
	private TextView mTxtDelegateSeconedSubTitle;
	// �ڶ����ӱ����е��ҵĲɹ�����������
	private TextView mTxtDelegateThirdSubNumber;
	// ����������Ŀ ����ɽ�����һ������
	private LinearLayout mLlDelegateThirdSubItem;
	// ����������Ŀ�ı���
	private TextView mTxtDelegateThirdSubTitle;
	// �������ӱ����е��ҵĲɹ�����������
	private TextView mTxtDelegateSeconedSubNumber;
	// ��־λ
	private boolean flag2 = true;

	/**
	 * �ҵ�ί��
	 */
	@SuppressLint("InflateParams")
	private void initMyDelegateContent() {
		// �ҵ�ί��
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_main_login_logistics, null);
		win_content.addView(view);

		mImgMyDelegateUpDownArrow = (ImageView) view
				.findViewById(R.id.img_up_down_arrow);
		mLlMyDelegateMainCotrol = (LinearLayout) view
				.findViewById(R.id.ll_main_cotrol);
		// FIXME Ĭ��������
		mLlMyDelegateSubAllTitle = (LinearLayout) view
				.findViewById(R.id.ll_sub_all_title_01);
		mLlMyDelegateSubAllTitle.setVisibility(View.GONE);
		mLlMyDelegateMainCotrol.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �����ҵ�ί���µ�����Ŀʱ��ʾ��������
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
		mTxtMyDelegateMainTitle.setText("�ҵ�ί��");
		mImgMyDelegateMainIcon = (ImageView) view
				.findViewById(R.id.img_main_icon);

		mImgMyDelegateMainIcon.setImageResource(R.drawable.user_entrust);
		mTxtDelegateMainNumber = (TextView) view
				.findViewById(R.id.txt_main_number);
		// �����ҵĲɹ�������
		mTxtDelegateMainNumber.setText(String.format(
				getString(R.string.main_title_num), 12));

		mLlDelegateFirstSubItem = (LinearLayout) view
				.findViewById(R.id.ll_first_sub_item);
		mLlDelegateFirstSubItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO ��ί�вɹ�������һҳ
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
		mTxtDelegateFirstSubTitle.setText("ί�вɹ�");
		mTxtDelegateFirstSubNumber = (TextView) view
				.findViewById(R.id.txt_first_sub_number);
		mTxtDelegateFirstSubNumber.setText(String.format(
				getString(R.string.main_title_num), 6));

		mLlDelegateSeconedSubItem = (LinearLayout) view
				.findViewById(R.id.ll_seconed_sub_item);
		mLlDelegateSeconedSubItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO ��ί�й�Ӧ������һҳ
				if (isLogin()) {
					goActivity(MemberLoginActivity.class);
					return;
				}
				goActivity(DelegationProviderActivity.class);
			}
		});
		mTxtDelegateSeconedSubTitle = (TextView) view
				.findViewById(R.id.txt_seconed_sub_title);
		mTxtDelegateSeconedSubTitle.setText("ί�й�Ӧ");
		mTxtDelegateSeconedSubNumber = (TextView) view
				.findViewById(R.id.txt_seconed_sub_number);
		mTxtDelegateSeconedSubNumber.setText(String.format(
				getString(R.string.main_title_num), 3));
		// ��������Ŀ
		mLlDelegateThirdSubItem = (LinearLayout) view
				.findViewById(R.id.ll_third_sub_item);
		mLlDelegateThirdSubItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO ��ί������������һҳ
				if (isLogin()) {
					goActivity(MemberLoginActivity.class);
					return;
				}
				goActivity(DelegationLogisticsActivity.class);
			}
		});
		mTxtDelegateThirdSubTitle = (TextView) view
				.findViewById(R.id.txt_third_sub_title);
		mTxtDelegateThirdSubTitle.setText("ί������");
		mTxtDelegateThirdSubNumber = (TextView) view
				.findViewById(R.id.txt_third_sub_number);
		mTxtDelegateThirdSubNumber.setText(String.format(
				getString(R.string.main_title_num), 3));
	}

	// �ҵĹ�Ӧ
	// ��������Ŀ��ʾ�������ص����Բ���
	private LinearLayout mLlMyProviderMainCotrol;
	// ����Ŀ�����֡�����
	private TextView mTxtMyProviderMainTitle;
	// ����Ŀ�ĵ�ͼ��
	private ImageView mImgMyProviderMainIcon;
	// ����Ŀ����Ϣ��Ŀ
	private TextView mTxtProviderMainNumber;
	// ���������»������ϼ�ͷ�Ŀ���
	private ImageView mImgMyProviderUpDownArrow;
	// �洢��ʾ������������Ŀ�����Բ���
	private LinearLayout mLlMyProviderSubAllTitle;
	// ��һ������Ŀ ����ɽ�����һ������
	private LinearLayout mLlProviderFirstSubItem;
	// ��һ������Ŀ�ı���
	private TextView mTxtProviderFirstSubTitle;
	// ��һ���ӱ����е��ҵĲɹ�����������
	private TextView mTxtProviderFirstSubNumber;
	// �ڶ�������Ŀ ����ɽ�����һ������
	private LinearLayout mLlProviderSeconedSubItem;
	// �ڶ�������Ŀ�ı���
	private TextView mTxtProviderSeconedSubTitle;
	// �ڶ����ӱ����е��ҵĲɹ�����������
	private TextView mTxtProviderSeconedSubNumber;
	// ����������Ŀ ����ɽ�����һ������
	private LinearLayout mLlDelegateThirdSubPro;
	// ����������Ŀ������
	private TextView mTxtDelegateThirdTitlePro;

	// ��־λ
	private boolean flag4 = true;

	// ���ĸ���Ŀ����ɽ�����һ������
	private LinearLayout mLlFourMyProduct;
	// ���ĸ���Ŀ������
	private TextView mTxtMyProductTitle;

	/**
	 * ���ǹ�Ӧ����Ŀ
	 */
	private void initMyProviderContent() {
		// �ҵĹ�Ӧ
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_main_login, null);
		win_content.addView(view);
		mImgMyProviderUpDownArrow = (ImageView) view
				.findViewById(R.id.img_up_down_arrow);
		mLlMyProviderMainCotrol = (LinearLayout) view
				.findViewById(R.id.ll_main_cotrol);
		// FIXME Ĭ��������
		mLlMyProviderSubAllTitle = (LinearLayout) view
				.findViewById(R.id.ll_sub_all_title_01);
		mLlMyProviderSubAllTitle.setVisibility(View.GONE);
		mLlMyProviderMainCotrol.setOnClickListener(new OnClickListener() {

			@SuppressLint("InflateParams")
			@Override
			public void onClick(View v) {
				if (info == null) {
					// displayToast("�û�δ��¼,���¼������.");
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
		mTxtMyProviderMainTitle.setText("���ǹ�Ӧ��");
		mImgMyProviderMainIcon = (ImageView) view
				.findViewById(R.id.img_main_icon);
		mImgMyProviderMainIcon.setImageResource(R.drawable.user_business);
		mTxtProviderMainNumber = (TextView) view
				.findViewById(R.id.txt_main_number);
		// �����ҵĲɹ�������
		mTxtProviderMainNumber.setText(String.format(
				getString(R.string.main_title_num), 12));

		mLlProviderFirstSubItem = (LinearLayout) view
				.findViewById(R.id.ll_first_sub_item);
		mLlProviderFirstSubItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �ҵĹ�Ӧ
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
		mTxtProviderFirstSubTitle.setText("�ҵĹ�Ӧ");
		mTxtProviderFirstSubNumber = (TextView) view
				.findViewById(R.id.txt_first_sub_number);
		mTxtProviderFirstSubNumber.setText(String.format(
				getString(R.string.main_title_num), 6));

		mLlProviderSeconedSubItem = (LinearLayout) view
				.findViewById(R.id.ll_seconed_sub_item);
		mLlProviderSeconedSubItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO �ɹ�Ӧ���������һҳ
				goActivity(ProviderIntentationOrderListActivity.class);
			}
		});
		mTxtProviderSeconedSubTitle = (TextView) view
				.findViewById(R.id.txt_seconed_sub_title);
		mTxtProviderSeconedSubTitle.setText("��Ӧ����");
		mTxtProviderSeconedSubNumber = (TextView) view
				.findViewById(R.id.txt_seconed_sub_number);
		mTxtProviderSeconedSubNumber.setText(String.format(
				getString(R.string.main_title_num), 6));

		// ��������Ŀ
		mLlDelegateThirdSubPro = (LinearLayout) view
				.findViewById(R.id.ll_third_sub_item);
		mLlDelegateThirdSubPro.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO �ɹ�Ӧ����������һҳ
				// goActivity(MyProviderOrderFormActivity.class);
				goActivity(MerchantOrderActivity.class);
			}
		});
		mTxtDelegateThirdTitlePro = (TextView) view
				.findViewById(R.id.txt_third_sub_title);
		mTxtDelegateThirdTitlePro.setText("��Ӧ����");

		// ���ĸ���Ŀ - �ҵĲ�Ʒ
		mLlFourMyProduct = (LinearLayout) view
				.findViewById(R.id.ll_four_sub_item);
		mLlFourMyProduct.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �����ҵĲ�Ʒ
				goActivity(MyProductActivity.class);
			}
		});
		mTxtMyProductTitle = (TextView) view
				.findViewById(R.id.txt_four_sub_title);
		mTxtMyProductTitle.setText("�ҵĲ�Ʒ");
	}

	/**
	 * ������ʾ�����û�ѡ���Ƿ���ת������Ӧ����פ��ҳ
	 */
	protected void EntryCompanyDialog() {
		displayMsgBoxE("��ʾ", "�Ƿ���빩Ӧ����פ ? ", new MsgBoxEOnClickListener() {

			@Override
			public void onClickListener() {
				// ���빩Ӧ����פ
				// displayToast("���빩Ӧ����פ����");
				goActivity(MerchantEntryActivity.class);
			}
		});
	}

	// �ҵĲɹ�
	// ��������Ŀ��ʾ�������ص����Բ���
	private LinearLayout mLlMyPurchaseMainCotrol;
	// ����Ŀ�����֡�����
	private TextView mTxtMyPurchaseMainTitle;
	// ����Ŀ�ĵ�ͼ��
	private ImageView mImgMyPurchaseMainIcon;
	// ����Ŀ����Ϣ��Ŀ
	private TextView mTxtMainNumber;
	// ���������»������ϼ�ͷ�Ŀ���
	private ImageView mImgMyPurchaseUpDownArrow;
	// �洢��ʾ������������Ŀ�����Բ���
	private LinearLayout mLlMyPurchaseSubAllTitle;
	// ��һ������Ŀ ����ɽ�����һ������
	private LinearLayout mLlFirstSubItem;
	// ��һ������Ŀ�ı���
	private TextView mTxtFirstSubTitle;
	// ��һ���ӱ����е��ҵĲɹ�����������
	private TextView mTxtFirstSubNumber;
	// �ڶ�������Ŀ ����ɽ�����һ������
	private LinearLayout mLlSeconedSubItem;
	// �ڶ�������Ŀ�ı���
	private TextView mTxtSeconedSubTitle;
	// �ڶ����ӱ����е��ҵĲɹ�����������
	private TextView mTxtSeconedSubNumber;
	// ����������Ŀ ����ɽ�����һ������
	private LinearLayout mLlDelegateThirdSub;
	// ����������Ŀ������
	private TextView mTxtDelegateThirdTitle;

	// ���ĸ���Ŀ����ɽ�����һ������
	private LinearLayout mLlFourShoopingBus;
	// ���ĸ���Ŀ������
	private TextView mTxtFourTitle;

	/**
	 * ���岿�� �ҵĲɹ���Ŀ
	 */
	private void initMyPurchaseContent() {
		// �ҵĲɹ�
		View myPurchaseView = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_main_login, null);
		win_content.addView(myPurchaseView);
		mImgMyPurchaseUpDownArrow = (ImageView) myPurchaseView
				.findViewById(R.id.img_up_down_arrow);
		// FIXME Ĭ��������
		mLlMyPurchaseSubAllTitle = (LinearLayout) myPurchaseView
				.findViewById(R.id.ll_sub_all_title_01);
		mLlMyPurchaseSubAllTitle.setVisibility(View.GONE);

		mLlMyPurchaseMainCotrol = (LinearLayout) myPurchaseView
				.findViewById(R.id.ll_main_cotrol);
		mLlMyPurchaseMainCotrol.setOnClickListener(this);
		mTxtMyPurchaseMainTitle = (TextView) myPurchaseView
				.findViewById(R.id.txt_main_title);
		mTxtMyPurchaseMainTitle.setText("���ǲɹ���");
		mImgMyPurchaseMainIcon = (ImageView) myPurchaseView
				.findViewById(R.id.img_main_icon);
		mImgMyPurchaseMainIcon.setImageResource(R.drawable.user_buyers);
		mTxtMainNumber = (TextView) myPurchaseView
				.findViewById(R.id.txt_main_number);
		// �����ҵĲɹ�������
		mTxtMainNumber.setText(String.format(
				getString(R.string.main_title_num), 10));

		mLlFirstSubItem = (LinearLayout) myPurchaseView
				.findViewById(R.id.ll_first_sub_item);
		mLlFirstSubItem.setOnClickListener(this);
		mTxtFirstSubTitle = (TextView) myPurchaseView
				.findViewById(R.id.txt_first_sub_title);
		mTxtFirstSubTitle.setText("�ҵĲɹ�");
		mTxtFirstSubNumber = (TextView) myPurchaseView
				.findViewById(R.id.txt_first_sub_number);
		mTxtFirstSubNumber.setText(String.format(
				getString(R.string.main_title_num), 5));
		mLlSeconedSubItem = (LinearLayout) myPurchaseView
				.findViewById(R.id.ll_seconed_sub_item);
		mLlSeconedSubItem.setOnClickListener(this);
		mTxtSeconedSubTitle = (TextView) myPurchaseView
				.findViewById(R.id.txt_seconed_sub_title);
		mTxtSeconedSubTitle.setText("�ɹ�����");
		mTxtSeconedSubNumber = (TextView) myPurchaseView
				.findViewById(R.id.txt_seconed_sub_number);
		mTxtSeconedSubNumber.setText(String.format(
				getString(R.string.main_title_num), 5));

		// ��������Ŀ
		mLlDelegateThirdSub = (LinearLayout) myPurchaseView
				.findViewById(R.id.ll_third_sub_item);
		mLlDelegateThirdSub.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO �ɲɹ�����������һҳ
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
		mTxtDelegateThirdTitle.setText("�ɹ�����");

		// ���ĸ���Ŀ - �ҵĹ��ﳵ
		mLlFourShoopingBus = (LinearLayout) myPurchaseView
				.findViewById(R.id.ll_four_sub_item);
		mLlFourShoopingBus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ���빺�ﳵ
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
		mTxtFourTitle.setText("�ҵĹ��ﳵ");
	}

	// ��־λ

	private boolean flag5 = true;
	private boolean flag6 = true;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_user_icon:// ͷ��ѡ��
			token = XmlUtils.getInstence(this).getXmlStringValue("token");
			if (!isLogin()) {
				fixPhoto(AppNetUrl.getPhotoStyleUrl(), "", 1);
			} else {
				goActivity(MemberLoginActivity.class);
			}
			break;
		case R.id.txt_user_nickname:// ������е�¼���߽����û����ý���
			token = XmlUtils.getInstence(this).getXmlStringValue("token");
			if (!TextUtils.isEmpty(token)) {
				goActivity(MemberCenterWebActivity.class);
			} else {
				goActivity(MemberLoginActivity.class);
			}
			break;
		case R.id.ll_main_cotrol:// �����ҵĲɹ��µ�����Ŀʱ��ʾ��������
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
		case R.id.ll_first_sub_item:// �����ҵĲɹ��µ�һ������Ŀ�ĵ���¼� �ҵĲɹ�
			if (isLogin()) {
				goActivity(MemberLoginActivity.class);
				return;
			} else {
				goActivity(MyPurchaseOrderActivity.class);
			}
			break;
		case R.id.ll_seconed_sub_item:// �����ҵĲɹ��µڶ�������Ŀ�ĵ���¼� �ɹ�����
			if (isLogin()) {
				goActivity(MemberLoginActivity.class);
				return;
			} else {
				goActivity(AlreadySendPurchaseOrderActivity.class, 0);
			}
			break;
		case R.id.ll_my_information_item:// �ҵ���Ϣ����Ŀ�ĵ���¼�
			goActivity(MyMsgListActivity.class);
			break;
		case R.id.ll_other_item:// ������Ŀ�ĵ���¼�
			goActivity(OtherActivity.class);
			break;
		case R.id.ll_help_item:// ������Ŀ�ĵ���¼�
			goActivity(HelperCenterActivity.class);
			break;
		case R.id.ll_main_collection:// �ҵ��ղأ���ע����Ŀ�ĵ���¼�

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
		case R.id.txt_register:// ע�ᰴť(���û����빩Ӧ����פ����)
			if (isLogin()) {
				// �ֻ�ע�����
				goActivity(PhoneRegisterActivity.class);
				return;
			}
			if (!info.isIscompany()) {
				// ���빩Ӧ����פ����(��ҵ��Աע��)
				goActivity(MerchantEntryActivity.class);
			} else {
				// ���빩Ӧ������(��ҵ��Ա����)
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
	 * �жϹ�Ӧ���Ƿ��¼
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
	 * �ж��Ƿ��¼
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
					mTxtUserNickName.setText("�ɹ�������");
					if (info.isIscompany()) {
						mTxtRegister.setText("��Ӧ������");
					} else {
						mTxtRegister.setText("��Ӧ����פ");
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
	 * ����ͷ��
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
			Log.d("TAG", "---�ӻ����л�ȡͷ��----");
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
		// x������ͷ��ʱ��ִ��һ�´���
		if (!isLogin()) {
			if (!isExecutiveOnResume) {
				return;
			}
			info = CmallApplication.getUserInfo();
			if (info != null) {
				mTxtUserNickName.setText("�ɹ�������");
				if (info.isIscompany()) {
					mTxtRegister.setText("��Ӧ������");
				} else {
					mTxtRegister.setText("��Ӧ����פ");
				} 
				Log.d("TAG", "��һ�ν�ȥ���ͼƬURL��"+info.getHeadPortrait());
				LoadHeadImg(info.getHeadPortrait());
			}
		} else {
			notLogin();
		}
	}

	private void notLogin() {
		mTxtUserNickName.setText("��¼");
		mTxtRegister.setText("ע��");
		mImgUserIcon.setBackgroundResource(R.drawable.icon);
		mLlMyProviderSubAllTitle.setVisibility(View.GONE);
		mImgMyProviderUpDownArrow.setImageResource(R.drawable.down_arrow);
		flag4 = true;
		info = null;
	}

	/*
	 * ��ȡ�޸�ͷ���ַ��ʵ����
	 */
	private AddressEntity addressEntity = null;
	public static final int GET_IMAGE_ADDRESS = 1;
	public static final int IMAGE_UPLOAD = 2;
	public static final int POST_PHOTO = 3;
	public static final int GET_PICTURE = 4;

	/**
	 * ��ȡ�޸�ͷ���·��
	 */
	private void fixPhoto(String url, String postData, final int state) {

		// �޸�ͷ���ʱ����onResume��ִ��
		isExecutiveOnResume = false;

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(url);
		jsonLoader.setPostData(postData);
		// ����ȡͼƬΪget���������Ķ���post����
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
					// ���������ؽ��
					if (1 == state) {
						resultInfo = JsonParse_User.getResultInfo(array);
					} else {
						resultInfo = JsonParse_User.getResultInfo1(array);
					}
				}

				switch (state) {
				case GET_IMAGE_ADDRESS:// ��ȡ�ϴ�ͼƬ�ĵ�ַ

					mImgUserIcon.setEnabled(true);
					if (NumEntity.PLEASE_LOG.equals(resultInfo.getMsg())) {
						displayToast(resultInfo.getMsg());
						// ������µ�¼
						XmlUtils.getInstence(UserActivity.this).delete();
						notLogin();
						isExecutiveOnResume = true;
						return;
					} else {

						addressEntity = ImageUploadManager
								.getFixPhotoAddressInfo(array);
						/*
						 * �����Ի���ѡ���޸�ͷ���ʱ����Ƭ����Դ
						 */
						new com.baiyi.cmall.views.UpLoadHeadImageDialog(
								UserActivity.this).show();
					}
					break;
				case IMAGE_UPLOAD:// �ϴ�ͼƬ֮���޸�ͷ��
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
				case POST_PHOTO:// �޸�ͷ��ɹ�
					if (resultInfo.getStatus() > 0) {
						fixPhoto(addressEntity.getImageaddress() + "?mid="
								+ CmallApplication.getUserInfo().getUserID(),
								"", GET_PICTURE);
					} else {
						isExecutiveOnResume = true;
						displayToast(resultInfo.getMsg());
					}
					break;
				case GET_PICTURE:// ��ȡͷ���ַ�ɹ�

					if (null != info) {
						isExecutiveOnResume = true;
						if (null != result) {
							displayToast("ͷ����³ɹ�");
							String pic = result.toString().trim();
							Log.d("TAG", "��һ�θ��³ɹ����ͼƬURL��"+pic);
							LoadHeadImg(pic);
							CatchUtils.saveXml(UserActivity.this, pic);
							// ͷ����Ϣ���ڻ����У����Ա�����գ��´����»�ȡ
							CatchUtils.updtateCacheLogin(UserActivity.this, pic);
						} else {
							displayToast("ͷ�����ʧ��");
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
