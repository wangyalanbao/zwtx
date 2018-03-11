package com.hxlm.health.web.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by dengyang on 15/7/30.
 */
public class StringUtils {

    public static String arrayToDelimitedString(Object[] arr, String delim) {
        if (arr == null || arr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(delim);
            }
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    public static String string2Unicode(String s) {
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)<=256)
            {
                sb.append("\\u00");
            }
            else
            {
                sb.append("\\u");
            }
            sb.append(Integer.toHexString(s.charAt(i)));
        }
        return sb.toString();
    }

    public static String unicode2String(String unicodeStr){
        StringBuffer sb = new StringBuffer();
        String str[] = unicodeStr.toUpperCase().split("U");
        for(int i=0;i<str.length;i++){
            if(str[i].equals("")) continue;
            char c = (char)Integer.parseInt(str[i].trim(),16);
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String ss = "你妹啊！aaaa";
        System.out.println(" == "+StringUtils.string2Unicode(ss));
    }


}