package com.lhq.Streaming.Utils;

/**
 * @author liu
 * @Create 2022-11-19
 * @Description 字符串处理工具类(后续在使用hbase的rowkey生成的时候，为了避免热点问题，将字符串进行反转)
 */
public class StringUtil {
    /**
     * 字符串倒序：有递归法（不推荐）、数组倒序拼接、冒泡对调、使用StringBuffer的reverse方法等。
     * 冒泡对调（推荐）
     * @param orig
     * @return
     */
    public static String reverse(String orig) {
        char[] s = orig.toCharArray();
        int n = s.length - 1;
        int halfLength = n / 2;
        for (int i = 0; i <= halfLength; i++) {
            char temp = s[i];
            s[i] = s[n - i];
            s[n - i] = temp;
        }
        return new String(s);
    }
}