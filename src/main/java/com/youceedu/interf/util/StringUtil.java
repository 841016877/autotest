package com.youceedu.interf.util;

public class StringUtil{
	
	public static String replaceStr(String sourceStr,String matchStr,String replaceStr){
		
		//得到matchStr左侧字符串
		int index = sourceStr.indexOf(matchStr);
		String beginStr = sourceStr.substring(0,index);
		
		//得到matchStr右侧字符串
		int matLength = matchStr.length();
		int sourLength = sourceStr.length();
		String endStr = sourceStr.substring(index+matLength,sourLength);
		
		//重新拼接
		sourceStr = beginStr+replaceStr+endStr;
		return sourceStr;
	}	
	

	public static void main(String[] args) {
		
	}

}
