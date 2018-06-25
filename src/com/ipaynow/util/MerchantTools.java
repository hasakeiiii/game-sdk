package com.ipaynow.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MerchantTools {
	public static String urlEncode(String paramString) {
		try {
			paramString = URLEncoder.encode(paramString, "UTF-8");
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
			localUnsupportedEncodingException.printStackTrace();
			return null;
		}
		return paramString;
	}
}
