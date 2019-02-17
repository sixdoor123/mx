package com.baiyi.jj.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.baiyi.core.file.Preference;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.theme.FontUtil;
import com.turbo.turbo.mexico.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.TELEPHONY_SERVICE;

public class Utils extends NumberUtils {


    public static String getDataBasePath() {
        String path = ContextUtil.getSDPath() + "/" + Config.Root_File_Path + "/database/";
        return path;
    }

    public static String getVersion(Context context) {
             try {
                   PackageManager manager = context.getPackageManager();
                     PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
                    String version = info.versionName;
                     return version;
                 } catch (Exception e) {
                     e.printStackTrace();
                     return "null";
                 }
         }

    public static String getAndroidVersion(){
        String verson =android.os.Build.MODEL + ","
                + android.os.Build.VERSION.SDK_INT;
        return verson;
    }

    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile(
                "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        System.out.println(matcher.matches());
        return matcher.matches();
    }

    public static boolean checkUserName(String userName) {
        //以字母开头，长度在4-25之间，只能包含字符、数字和下划线  "^[a-zA-Z]\w{5,15}$"
        String reg = "^[a-zA-Z]\\w{3,24}$";
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(userName).matches();
    }

    public static boolean checkHasOther(String userName) {
        String reg = "^[a-zA-Z0-9]\\w{1,100}$";
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(userName).matches();
    }
    public static String checkHasOths(String userName) {
        String check = "";
        String reg = "[^a-zA-Z0-9]";
        Pattern pattern = Pattern.compile(reg);
        Matcher m = pattern.matcher(userName);
        check = m.replaceAll("").trim();
        return check;
    }

    public static String ToDBC(String input) {
        if (Utils.isStringEmpty(input)) {
            return "";
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        Pattern p = Pattern.compile("[1][358]\\d{9}");
        Matcher m = p.matcher(phoneNumber);
        if (m.matches()) {
            return true;
        } else {
            /*
             * if (phoneNumber.length() != 11) { return false; }
			 */
            /*
			 * boolean isValid = false; String expression =
			 * "^//(?(//d{3})//)?[- ]?(//d{3})[- ]?(//d{5})$"; String
			 * expression2 = "^//(?(//d{3})//)?[- ]?(//d{4})[- ]?(//d{4})$";
			 * CharSequence inputStr = phoneNumber;
			 * 
			 * Pattern pattern = Pattern.compile(expression); Matcher matcher =
			 * pattern.matcher(inputStr); Pattern pattern2 =
			 * Pattern.compile(expression2); Matcher matcher2 =
			 * pattern2.matcher(inputStr);
			 * 
			 * if (matcher.matches() || matcher2.matches()) { isValid = true; }
			 */
            return false;
        }
    }

    public static StaticLayout generateLayout(String text, int width, Context context) {
        TextPaint paint = new TextPaint();
        int size = (int) FontUtil.getNewsTitleSize(context);
        paint.setTextSize(getTextSize(context, TypedValue.COMPLEX_UNIT_PX, size));
        return new StaticLayout(text, paint, width, Layout.Alignment.ALIGN_NORMAL, 1.5f, 1.0f, false);
    }

    public static int getTextSize(Context context, int unit, int size) {
        int mTextSize;
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                mTextSize = size;
                return mTextSize;
            case TypedValue.COMPLEX_UNIT_DIP:
                mTextSize = dp2px(context, size);
                return mTextSize;
            case TypedValue.COMPLEX_UNIT_SP:
                mTextSize = sp2px(context, size);
                return mTextSize;
        }
        return size;
    }

    public static int sp2px(Context context, int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    public static int dp2px(Context context, int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());

    }

    public static String getMobileId(Context context) {
        String mobileId = ZSIMInfo.getDeviceID(context) + "00";
        return mobileId;
    }

    public static String replaceRefund(String sourceStr, String[][] strings) {
        String temp = sourceStr;
        for (int i = 0; i < strings.length; i++) {
            String[] result = strings[i];
            Pattern pattern = Pattern.compile(result[0]);
            Matcher matcher = pattern.matcher(temp);
            temp = matcher.replaceAll(result[1]);
        }
        return temp;

    }

    /**
     * ���������ֵĳ��ȸı��С
     */
    public static void setTextChange(Context context, TextView tv, String str, float actleng) {

        tv.setText(str);

        DisplayMetrics dm = context.getApplicationContext().getResources()
                .getDisplayMetrics();
        float density = dm.density;
        float textsize = (tv.getTextSize() / density);  //���ֵĴ�С
        float textlength = getTextLength(tv);  // ���ֵĳ���

        while (textlength > actleng) {  // ���ֵĳ��ȴ���ʵ����ռ����ʱ
            textsize--;
            tv.setTextSize(textsize);
            textlength = getTextLength(tv);
        }

    }

    public static float getScreeWidth(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources()
                .getDisplayMetrics();
        float width = dm.widthPixels;
        return width;
    }

    public static float getTextLength(TextView textView) {
        Paint paint = new Paint();
        paint.setTextSize(textView.getTextSize());
        float size = paint.measureText(textView.getText().toString());
        return size;
    }

    public static float getTextLines(Context context, float size, String str, float leng) {
        TextView tv = new TextView(context);
        tv.setText(str);

        DisplayMetrics dm = context.getApplicationContext().getResources()
                .getDisplayMetrics();
        float density = dm.density;
        tv.setTextSize(size);
        float textlength = getTextLength(tv);  // ���ֵĳ���
        float line = textlength / leng;
        return line;
    }

    public static float getTextlineCouts(Context context, float size, String str, int leng) {
        TextView tv = new TextView(context);
        tv.setText(str);
        tv.setWidth(leng);
        tv.setTextSize(size);
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        tv.measure(w, h);
        int count = tv.getLineCount();
        return count;
    }


    public static int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top);
    }

//	int size = (int)FontUtil.getNewsTitleTextSize(context);

    public static boolean isPhoneNum(String phoneNumber) {

        if (phoneNumber.length() == 11 || phoneNumber.length() == 7
                || phoneNumber.length() == 8) {
            return true;
        } else {
            return false;
        }
    }

    // ��.��ͷ������ת����0.��ͷ
    public static String getTrueNumber(String number) {
        if (!Utils.isStringEmpty(number)) {
            number.substring(0, 1).equals(".");
            return "0" + number;
        } else {
            return "0";
        }
    }

    public byte[] eccrypt(String info) throws NoSuchAlgorithmException {
        // ����MD5�㷨����MessageDigest����
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] srcBytes = info.getBytes();
        // ʹ��srcBytes����ժҪ
        md5.update(srcBytes);
        // ��ɹ�ϣ���㣬�õ�result
        byte[] resultBytes = md5.digest();
        return resultBytes;
    }

    /**
     * ������λС��
     *
     * @param d
     * @return
     */
    public static String getPoint2Float(double d) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        return decimalFormat.format(d);
    }

    public static String getMD5Code(String inStr) {

        MessageDigest md = null;

        String out = null;

        try {

            md = MessageDigest.getInstance("MD5");

            byte[] digest = md.digest(inStr.getBytes());

            out = byte2hex(digest);

        } catch (NoSuchAlgorithmException e) {

            TLog.d(e.toString());
            e.printStackTrace();

        }

        return out.toUpperCase();

    }

    private static String byte2hex(byte[] b) {

        String hs = "";

        String stmp = "";

        for (int n = 0; n < b.length; n++) {

            stmp = (Integer.toHexString(b[n] & 0XFD));

            if (stmp.length() == 1) {

                hs = hs + "0" + stmp;

            } else {

                hs = hs + stmp;

            }

        }

        return hs;

    }

    public static boolean isSDCardExists() {
        return Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * @return ������ByteΪ��λ
     */
    public static long getSpace() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            String sdcard = Environment.getExternalStorageDirectory().getPath();
            StatFs statFs = new StatFs(sdcard);
            long blockSize = statFs.getBlockSize();
            long blocks = statFs.getAvailableBlocks();
            long availableSpare = blocks * blockSize;
            // Logger.error("ʣ��ռ�", "availableSpare = " + availableSpare);
            // if (availableSpare > sizeMb) {
            // ishasSpace = true;
            // }
            return availableSpare;
        }
        return 0;
    }

    public static boolean isNum(String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    public static boolean getSDCard1M() {
        if (getSpace() < 1)
            return false;
        return true;
    }

    // public static void loginRemind(final Context context, String title,
    // String message) {
    // AlertDialog.Builder alterBuilder = new AlertDialog.Builder(context);
    // alterBuilder.setTitle(title);
    // alterBuilder.setMessage(message);
    // alterBuilder.setPositiveButton("ȷ��",
    // new DialogInterface.OnClickListener() {
    //
    // @Override
    // public void onClick(DialogInterface dialog, int which) {
    // dialog.cancel();
    // Intent intent = new Intent();
    // intent.setClass(context, LoginActivity.class);
    // context.startActivity(intent);
    //
    // }
    // });
    // alterBuilder.setNegativeButton("ȡ��",
    // new DialogInterface.OnClickListener() {
    //
    // @Override
    // public void onClick(DialogInterface dialog, int which) {
    // dialog.cancel();
    //
    // }
    // });
    // alterBuilder.create().show();
    // }

    public static boolean isStringEmpty(String data) {
        if (data == null || "".equals(data.trim())|| "null".equals(data))
            return true;
        return false;
    }

    public static boolean isStringEmpty(List<?> list) {
        if (list == null || list.size() == 0)
            return true;
        return false;
    }

    public static String getBase64Utf(String data) {
        try {
            return Utils.isStringEmpty(data) ? "" : Base64.encode(data
                    .getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
        }
        return "";
    }


    // public static int getDensityDpi(Context context) {
    // DisplayMetrics dm = new DisplayMetrics();
    // dm = context.getApplicationContext().getResources().getDisplayMetrics();
    // return dm.densityDpi;
    // }

    // public static int dip2px(Context context, float dip) {
    // int dpi = getDensityDpi(context);
    // return (int) ((dip / 160.0f) * dpi);
    // }

    /**
     * dipתΪ px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px תΪ dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static Vector2Int getMaxWindowDisplaySize(Context context) {
        Rect rect = new Rect();
        ((Activity) context).getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(rect);
        return new Vector2Int(rect.width(), rect.height());
    }

    public static Vector2Int getScreenSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return new Vector2Int(dm.widthPixels, dm.heightPixels);
    }

    public static String getCount4(String sr) {
        if (isStringEmpty(sr))
            return null;
        return null;
    }

    public static void goIntentTel(Context context, String phoneNum) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DIAL");
        intent.setData(Uri.parse("tel:" + phoneNum));
        context.startActivity(intent);
    }

    public static String[] split(String original, String regex) {
        int startIndex = 0;
        Vector v = new Vector();
        String[] str = null;
        int index = 0;
        startIndex = original.indexOf(regex);
        while (startIndex < original.length() && startIndex != -1) {
            String temp = original.substring(index, startIndex);
//            System.out.println(" " + startIndex);
            v.addElement(temp);
            index = startIndex + regex.length();
            startIndex = original.indexOf(regex, startIndex + regex.length());
        }
        v.addElement(original.substring(index + 1 - regex.length()));
        str = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            str[i] = (String) v.elementAt(i);
        }
        return str;
    }
    public static List<String> splitToList(String original, String regex) {
        int startIndex = 0;
        Vector v = new Vector();
        List<String> str = new ArrayList<>();
        int index = 0;
        startIndex = original.indexOf(regex);
        while (startIndex < original.length() && startIndex != -1) {
            String temp = original.substring(index, startIndex);
//            System.out.println(" " + startIndex);
            v.addElement(temp);
            index = startIndex + regex.length();
            startIndex = original.indexOf(regex, startIndex + regex.length());
        }
        v.addElement(original.substring(index + 1 - regex.length()));
        for (int i = 0; i < v.size(); i++) {
            str.add((String) v.elementAt(i));
        }
        return str;
    }

    // public static String getHiddenPhoneNum(String phoneNum) {
    // String start = phoneNum.substring(0, 3);
    // String end = phoneNum.substring(7, phoneNum.length());
    // return start + "****" + end;
    // }

    public static String getTimeFormat(String timeStr) {
        if (timeStr.equals("") || timeStr == null) {
            return "";
        } else {
            String time = timeStr.substring(0, timeStr.lastIndexOf("-") + 3);
            return timeStr == null ? "û����" : time;
        }
    }


    // �����Ǯ������ ȥ��С����
    public static String getIntegerToPrice(double d) {
        int n = (int) d;
        if ((d - n) == 0) {
            return String.valueOf(n);
        } else {
            return String.valueOf(d);
        }
    }

    /**
     * ���ַ������з���
     *
     * @param string 279225644
     * @param num    3
     * @return 279 225 644
     */
    public static String getFomatStr(String string, int num) {
        if (isStringEmpty(string)) {
            return "";
        }
        String Str = string.replace(" ", "");
        char[] chars = Str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < chars.length + 1; i++) {
            sb.append(chars[i - 1]);
            if (i % num == 0 && i != 0) {
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    /**
     * ���ֻ�����������ش���
     *
     * @param phone 18712345678
     * @return 187****5678
     */
    public static String getHiddenPhone(String phone) {
        if (isStringEmpty(phone)) {
            return "";
        }
        String str = phone.replace(" ", "");
        return str.substring(0, 3) + "****" + str.substring(7, str.length());
    }

    /**
     * �����û���
     *
     * @return
     */
    public static String getHiddenName(String name) {
        if (isStringEmpty(name)) {
            return "";
        }
        String str = name.replace(" ", "");
        return str.substring(0, 1) + "****";
    }


    public static String[] getStringToList(List<ChannelItem> list) {
        if (Utils.isStringEmpty(list)) {
            return null;
        }
        String[] sb = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            sb[i] = list.get(i).getCid();
        }
        return sb;
    }

    public static String getString2ListInteger(List<Integer> tags) {
        if (isStringEmpty(tags)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tags.size(); i++) {
            sb.append(tags.get(i));
            if (i != tags.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static List<Integer> getTagsList(String tag) {
        if (Utils.isStringEmpty(tag)) {
            return null;
        }
//        List<Integer> tagss = new ArrayList<Integer>();
//        tagss.add(6);
//        tagss.add(7);
//        return tagss;
        List<Integer> tags = new ArrayList<Integer>();
        String[] tagArray = tag.split(",");
        for (int i = 0; i < tagArray.length; i++) {
            if (Utils.isStringEmpty(tagArray[i])) {
                continue;
            }
            tags.add(Integer.parseInt(tagArray[i]));
        }
        return tags;
    }

    public static String getStringToInts(int[] tags) {
        if (tags == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tags.length; i++) {
            sb.append(tags[i]);
            if (i != tags.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static List<Integer> getTagsList(int[] tags) {
        if (tags == null) {
            return null;
        }
        List<Integer> tagsList = new ArrayList<Integer>();
        for (int i = 0; i < tags.length; i++) {
            tagsList.add(tags[i]);
        }
        return tagsList;
    }

    public static int[] getTagsArray(String tag) {
        if (Utils.isStringEmpty(tag)) {
            return null;
        }
        String[] tagArray = tag.split(",");
        int[] tags = new int[tagArray.length];
        for (int i = 0; i < tagArray.length; i++) {
            tags[i] = Integer.parseInt(tagArray[i]);
        }
        return tags;
    }

    /**
     * ���ַ����еĶ���ת��Ϊ�ո�
     *
     * @param str
     * @return
     */
    public static String getCommaToSpace(String str) {
        if (isStringEmpty(str)) {
            return "";
        }
        // String string = str.replace(" ", "");

        return str.replace(",", "  ");
    }

    // �ַ�������תList<String>
    public static String[] switchToStringArray(List<String> list) {
        String[] strings = new String[list.size()];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = list.get(i);
        }
        return strings;
    }

    // �ַ�������תList<String>
    public static List<String> switchToStringList(String[] strings) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < strings.length; i++) {
            list.add(strings[i]);
        }
        return list;
    }

    // �ж�һ���ַ����������Ƿ����ĳһ���ַ���
    public static boolean isExistInStringArray(String[] strings, String recom) {
        boolean isExist = false;
        for (String string : strings) {
            if (string.equals(recom)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    // �ж�һ��List<String>���Ƿ����ĳһ���ַ���
    public static boolean isExistInStringList(List<String> list, String recom) {
        boolean isExist = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(recom)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    public static int pixToDip(int pix) {
        int Dip = 0;

        return Dip;
    }

    public static byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options，把压缩后的数据存放到baos中
            options -= 8;// 每次都减少8
            if (options<=20) {
                break;
            }
        }
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

    public static void setBright(Activity context, int bright) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, bright);

        bright = Settings.System.getInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, -1);

        float brightFloat = (float) bright / 255;
        if (brightFloat > 0 && brightFloat < 1) {
            lp.screenBrightness = brightFloat;
        }
        context.getWindow().setAttributes(lp);
    }

    public static int getBright(Activity context) {
        int bright = Settings.System.getInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, 255);
        return bright;
    }

    public static String getChannelPostData(List<ChannelItem> channelList) {
        if (isStringEmpty(channelList)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < channelList.size(); i++) {
            sb.append(channelList.get(i).getCid());
            if (i != channelList.size() - 1) {
                sb.append("|");
            }
        }
        return sb.toString();
    }

    public static String stringForTime(int timeMs) {
        if (timeMs <= 0 || timeMs >= 24 * 60 * 60 * 1000) {
            return "00:00";
        }
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    public static String stringForTime2(int time) {
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }

    public static String getNoNullString(String display) {
        if (null == display || "".equals(display) || "null".equals(display)) {
            return "";
        } else {
            return display;
        }
    }

}
