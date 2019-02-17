package com.baiyi.jj.app.theme;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.utils.Utils;

/**
 * 
 * @author tangkun
 *
 */
public class ThemeManager {
	
	private static ThemeManager instance = null;

	private Context deafultContext = null;
	private Context currentContext = null;
	private LayoutInflater currentInflater = null;
	private LayoutInflater defalutInflater = null;
	
	private static final String Theme_Color_Xml = "theme_color";
	private static final String Theme_TextSize_Xml = "";
	
	public static final String Id_defType = "id";
	
	private ThemeManager()
	{
		
	}
	
	public static ThemeManager getThemeManager()
	{
		if(instance == null)
		{
			instance = new ThemeManager();
		}
		return instance;
	}

	/**
	 * 设置皮肤包
	 * 
	 * @param context
	 * @param pageInfo
	 * @return
	 */
	public void setSkinContext(Context context, PackageInfo pageInfo) {
		String packageName = "";
		try {
			packageName = pageInfo.packageName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		setSkinContext(context, packageName);
	}

	/**
	 * 获取皮肤包
	 * 
	 * @return
	 */
	public String getCurrentSkinPackageName() {
		if (currentContext != null) {
			return currentContext.getPackageName();
		}
		return "";
	}

	public void setSkinContext(Context context, String packageName) {

		deafultContext = context;
		try {
			currentContext = context.createPackageContext(packageName,
					Context.CONTEXT_INCLUDE_CODE
							| Context.CONTEXT_IGNORE_SECURITY);

		} catch (Exception e) {
			e.printStackTrace();
			currentContext = context;
		}
		try {
			currentInflater = (LayoutInflater) currentContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			defalutInflater = (LayoutInflater) deafultContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取当前皮肤包
	 * 
	 * @param context
	 * @return
	 */
	public Context getSkinContext(Context context) {
		if (currentContext == null) {
			return context;
		} else {
			return currentContext;
		}
	}

	/**
	 * 根据资源获取视图
	 *
	 * @param root
	 * @param attachToRoot
	 * @return
	 */
	public View getLayoutFromResource(Context context,
			String layoutName, ViewGroup root, boolean attachToRoot) {
		View resultView = null;

		try {
			Context ct = getSkinContext(context);
			int resid = ct.getResources().getIdentifier(layoutName, "layout",
					ct.getPackageName());
			if (resid != 0) {
				resultView = currentInflater.inflate(resid, root, attachToRoot);
			} else {
				resid = context.getResources().getIdentifier(layoutName,
						"layout", context.getPackageName());
				resultView = defalutInflater.inflate(resid, root, attachToRoot);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return resultView;
	}

	public Drawable getDrawable(Context context, String drawableName)
			throws NotFoundException {
		Drawable resultDrawable = null;
		Context ct = getSkinContext(context);
		try {
			int resid = getIdentifier2(ct, drawableName.trim(), "drawable");
			if (resid == 0) {
				resid = getIdentifier2(context, drawableName.trim(), "drawable");
				resultDrawable = context.getResources().getDrawable(resid);
			} else {
				resultDrawable = ct.getResources().getDrawable(resid);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return resultDrawable;
	}

	public int getIdentifier2(Context context, String name,
			String defType) throws NotFoundException {
		int result = 0;
		try {
			result = context.getResources().getIdentifier(name, defType,
					context.getPackageName());
		} catch (Exception e) {
			e.printStackTrace();

		}
		return result;
	}

	public int getIdentifier(Context context, String name, String defType)
			throws NotFoundException {
		int result = 0;
		try {
			Context ct = getSkinContext(context);
			result = ct.getResources().getIdentifier(name, defType,
					ct.getPackageName());
			if (result == 0) {
				result = context.getResources().getIdentifier(name, defType,
						context.getPackageName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = context.getResources().getIdentifier(name, defType,
					context.getPackageName());
		}
		return result;
	}

	public Drawable getDrawableByPath(Context context,
			String imagePath) {
		try {

			if (Utils.isStringEmpty(imagePath))
				return null;

			Bitmap bm = BitmapFactory.decodeFile(imagePath);

			bm.setDensity(context.getResources().getDisplayMetrics().DENSITY_HIGH);

			Drawable dw = new BitmapDrawable(context.getResources(), bm);

			return dw;

		} catch (Exception e) {
			e.printStackTrace();
		} catch (OutOfMemoryError ex) {
			System.gc();
			ex.printStackTrace();
		}
		return null;
	}

	public boolean isInstallPackageName(Context context,
			String packageName) {
		PackageManager pm = context.getPackageManager();
		try {
			pm.getPackageGids(packageName);
			return true;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Document getDocument(InputStream is) throws Exception {
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbfactory.newDocumentBuilder();
		return db.parse(is);
	}

	public int getColorByName(String name) {
		int res = -1;
		try {

			Document doc = getDocumentByFile(Theme_Color_Xml);
			if (doc != null) {
				String value = getColorByName(doc, name);
				res = Color.parseColor(value);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}

	private synchronized Document getDocumentByFile(String xmlFileName)
			throws Exception {

		String path = ThemeConstant.SKIN_DIR + Theme_Color_Xml;
		File file = new File(path);
		if (file.exists()) {

			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(file);
				return getDocument(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return getDocument(CmsApplication.getApp().getAssets().open(xmlFileName));

	}

	private static String getColorByName(Document doc, String name) {
		NodeList nodeList = doc.getElementsByTagName(name);
		String res = null;
		if (nodeList != null) {
			int len = nodeList.getLength();
			if (len > 0) {
				Element el = (Element) nodeList.item(0);
				res = el.getAttribute("value");

			} else {

			}
		}
		return res;
	}

}
