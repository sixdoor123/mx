package com.baiyi.cmall.request.manager;

import java.util.List;

import com.baiyi.cmall.activities.main.mall.pop.entity.Qcm;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;

/**
 * �̳ǲ�������
 * 
 * @author lizl
 */
public class MallManager {

	/**
	 * key String ������ cy String ��� bd String Ʒ�� on String ���� dy String ����� pr
	 * String �۸� ps Integer ÿҳ��ʾ���� pi Integer ҳ�� sf int �����ֶ� st int �������� pro
	 * String ��������(����)
	 */

	// ����
	private static String sale = "";
	// �۸�
	private static String price = "";
	// ����
	private static String search = "";

	/**
	 * ��Ʒ�ķ����б�
	 * 
	 * @param cc
	 * @param pi
	 * @param ps
	 * @param state
	 *            2: ����
	 * @param qcms
	 * @return
	 */
	public static String getCategoryListData(String cc, int state, List<Qcm> qcms, int pi, int ps) {
		//
		StringBuilder builder = new StringBuilder();
		if (!Utils.isStringEmpty(cc)) {
//			builder.append("cy").append("=").append(cc).append("&");
		}
		builder.append("pi").append("=").append(pi).append("&");
		builder.append("ps").append("=").append(ps).append("&");

		// Ĭ������
		if (TextViewUtil.isStringEmpty(sale)) {
			StringBuilder saleBuilder = new StringBuilder();
			saleBuilder.append("sf").append("=").append(4).append("&");
			saleBuilder.append("st").append("=").append(0).append("&");
			sale = saleBuilder.toString();
		}

		// Ĭ�ϼ۸�
		if (TextViewUtil.isStringEmpty(price)) {
			StringBuilder priceBuilder = new StringBuilder();
			priceBuilder.append("pr").append("=").append(4).append("&");
			price = priceBuilder.toString();
		}

		switch (state) {
		case 1:// ����
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("key").append("=").append(cc).append("&");
			search = sBuilder.toString();
			break;
		case 2:// ����
			StringBuilder saleB = new StringBuilder();
			saleB.append("sf").append("=").append(cc).append("&");
			saleB.append("st").append("=").append(0).append("&");
			sale = saleB.toString();
			break;
		case 3:// �۸�
				// Ĭ�ϼ۸�
			StringBuilder priceB = new StringBuilder();
			priceB.append("pr").append("=").append(cc).append("&");
			price = priceB.toString();
			break;

		case -1:// ��λ
			break;
		}

		builder.append(sale)/*.append(price)*/.append(search);

		// ɸѡ
		if (qcms != null && qcms.size() > 0)

		{
			for (Qcm qcm : qcms) {
				if (!qcm.getSubName().equals("ȫ��")) {
					// �õ���������
					String fif = getFif(qcm.getQn());
					builder.append(fif).append("=").append(qcm.getBd()).append("&");
				}
			}
		}

		return builder.toString();
	}

	private static String getFif(String qn) {

		if ("���".equals(qn)) {
			return "cy";
		} else if ("Ʒ��".equals(qn)) {
			return "bd";
		} else if ("����".equals(qn)) {
			return "on";
		} else if ("�����".equals(qn)) {
			return "dy";
		} else if ("�۸�".equals(qn)) {
			return "pr";
		}
		return "pro";
	}

	/**
	 * ��Ʒ��ҳ����
	 * 
	 * @param pi
	 * @param ps
	 * @return
	 */
	public static String getListData(int pi, int ps) {
		//
		StringBuilder builder = new StringBuilder();

		builder.append("pi").append("=").append(pi).append("&");
		builder.append("ps").append("=").append(ps);
		return builder.toString();
	}

	/**
	 * ��ע�̼�
	 * 
	 * @param id
	 * @return
	 */
	public static String getAddCompanyData(int id) {
		StringBuilder builder = new StringBuilder();
		builder.append("targetid").append("=").append(id);
		return builder.toString();
	}

}
