package com.hxlm.health.web.yuntongxun.common;

import java.security.MessageDigest;

public class MD5 {

	/*
	 * 返回32位大写的MD5码
	 */
	public static String getEncoderByMd5(String sessionid) {
		try {
			StringBuffer hexString = null;
			byte[] defaultBytes = sessionid.getBytes();
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();

			hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				if (Integer.toHexString(0xFF & messageDigest[i]).length() == 1) {
					hexString.append(0);
				}
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			messageDigest.toString();
			sessionid = hexString + "";
			System.out.println(hexString.toString().toUpperCase());
			return hexString.toString().toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String md5Digest(String src) {
		MessageDigest md = null;
		byte[] b = null;
		try {
			md = MessageDigest.getInstance("MD5");
			b = md.digest(src.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return byte2HexStr(b);
	}

	public static String byte2HexStr(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; ++i) {
			String s = Integer.toHexString(b[i] & 0xFF);
			if (s.length() == 1)
				sb.append("0");

			sb.append(s.toUpperCase());
		}
		return sb.toString();
	}

}
