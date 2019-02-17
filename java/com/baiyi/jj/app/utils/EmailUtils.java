package com.baiyi.jj.app.utils;

/**
 * Created by Administrator on 2016/9/20.
 */

public class EmailUtils {

    /**
     * QQ Email Validate
     * 924587628@qq.com
     * @param email
     * @return
     */
    public static boolean isEmail(String email)
    {
        Boolean isEmail = false;
        String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (email.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }

    public static boolean isValidateEmail(String email) {
        //正则表达式
  /*
    String regex = "^[A-Za-z]{1,40}@[A-Za-z0-9]{1,40}\\.[A-Za-z]{2,3}$";
    return email.matches(regex);
   */

        //不适用正则
        if (email == null || "".equals(email)) return false;
        if (!containsOneWord('@', email) || !containsOneWord('.', email)) return false;
        String prefix = email.substring(0, email.indexOf("@"));
        String middle = email.substring(email.indexOf("@") + 1, email.indexOf("."));
        String subfix = email.substring(email.indexOf(".") + 1);
        System.out.println("prefix=" + prefix + "  middle=" + middle + "  subfix=" + subfix);

        if (prefix == null || prefix.length() > 40 || prefix.length() == 0) return false;
        if (!isAllWords(prefix)) return false;
        if (middle == null || middle.length() > 40 || middle.length() == 0) return false;
        if (!isAllWordsAndNo(middle)) return false;
        if (subfix == null || subfix.length() > 3 || subfix.length() < 2) return false;
        if (!isAllWords(subfix)) return false;
        return true;
    }

    //判断字符串只包含指定的一个字符c
    private static boolean containsOneWord(char c, String word) {
        char[] array = word.toCharArray();
        int count = 0;
        for (Character ch : array) {
            if (c == ch) {
                count++;
            }
        }
        return count == 1;
    }

    //检查一个字符串是否全部是字母
    private static boolean isAllWords(String prefix) {
        char[] array = prefix.toCharArray();
        for (Character ch : array) {
            if (ch < 'A' || ch > 'z' || (ch < 'a' && ch > 'Z')) return false;
        }
        return true;
    }

    //检查一个字符串是否包含字母和数字
    private static boolean isAllWordsAndNo(String middle) {
        char[] array = middle.toCharArray();
        for (Character ch : array) {
            if (ch < '0' || ch > 'z') return false;
            else if (ch > '9' && ch < 'A') return false;
            else if (ch > 'Z' && ch < 'a') return false;
        }
        return true;
    }
}
