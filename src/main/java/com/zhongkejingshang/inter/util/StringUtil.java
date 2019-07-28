package com.zhongkejingshang.inter.util;

public class StringUtil {
	public static String replaceStr(String sourceStr,String matcherStr,String replaceStr){
		
		try {
			int index= sourceStr.indexOf(matcherStr);
			String beginStr = sourceStr.substring(0, index);
			
			int matcherLen = matcherStr.length();
			int sourceLen = sourceStr.length();
			String endStr = sourceStr.substring(index+sourceLen, sourceLen);
			
			
			sourceStr = beginStr+replaceStr+endStr;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return sourceStr;
		
	}
}
