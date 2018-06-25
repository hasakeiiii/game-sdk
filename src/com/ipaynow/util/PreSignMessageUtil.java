package com.ipaynow.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PreSignMessageUtil {
	public String appId = null;
	public String mhtOrderNo = null;
	public String mhtOrderName = null;
	public String mhtOrderType = null;
	public String mhtCurrencyType = null;
	public String mhtOrderAmt = null;
	public String mhtOrderDetail = null;
	public String mhtOrderTimeOut = null;
	public String mhtOrderStartTime = null;
	public String notifyUrl = null;
	public String mhtCharset = null;
	public String payChannelType = null;
	public String mhtReserved = null;
	public String consumerId = null;
	public String consumerName = null;


	public String generatePreSignMessage() {
		Object localObject1 = new HashMap();
		try {
			Object localObject2 = null;
			Field[] arrayOfField;
			int j = (arrayOfField = this.getClass().getFields()).length;
			for (int i = 0; i < j; i++) {
				String str1 = ((Field) (localObject2 = arrayOfField[i]))
						.getName();
				String str2;
				if (((str2 = (String) ((Field) localObject2).get(this)) == null)
						|| (str2.equals(""))) {
					if ((!str1.equals("mhtOrderTimeOut"))
							&& (!str1.equals("payChannelType"))
							&& (!str1.equals("mhtReserved"))
							&& (!str1.equals("consumerId"))
							&& (!str1.equals("consumerName")))
						return null;
				} else
					((HashMap) localObject1).put(
							((Field) localObject2).getName(),
							(String) ((Field) localObject2).get(this));
			}
			localObject1 = createLinkString((HashMap) localObject1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return (String) localObject1;
	}

	private static String createLinkString(HashMap paramHashMap) {
		String str1 = "=";
		String str2 = "&";
		ArrayList localArrayList;
		Collections.sort(localArrayList = new ArrayList(paramHashMap.keySet()));
		StringBuilder localStringBuilder = new StringBuilder();
		for (int i = 0; i < localArrayList.size(); i++) {
			String str3 = (String) localArrayList.get(i);
			String str4 = (String) paramHashMap.get(str3);
			if (i == localArrayList.size() - 1)
				localStringBuilder.append(str3).append(str1).append(str4);
			else
				localStringBuilder.append(str3).append(str1).append(str4)
						.append(str2);
		}
		return localStringBuilder.toString();
	}

	public String generatePreSignMessage2() {
		Object localObject1 = new HashMap();
		try {
			Field localObject2 = null;
			Field[] arrayOfField;
			int j = (arrayOfField = PreSignMessageUtil.class
					.getDeclaredFields()).length;
			for (int i = 0 ; i < j; i++) {
				localObject2 = arrayOfField[i];
				
				String str1 = localObject2.getName();
				String str2;
				if (((str2 = (String) localObject2.get(this)) == null)
						|| (str2.equals(""))) {
					if ((!str1.equals("mhtOrderTimeOut"))
							&& (!str1.equals("payChannelType"))
							&& (!str1.equals("mhtReserved"))
							&& (!str1.equals("consumerId"))
							&& (!str1.equals("consumerName")))
						return null;
				} else
					((HashMap) localObject1).put(localObject2.getName(),
							(String) localObject2.get(this));
			}
			localObject1 = createLinkString((HashMap) localObject1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return (String) localObject1;
	}
}