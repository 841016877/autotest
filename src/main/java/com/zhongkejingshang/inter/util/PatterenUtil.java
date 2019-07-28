package com.zhongkejingshang.inter.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.alibaba.fastjson.JSONPath;
import junit.framework.Assert;

public class PatterenUtil {
	
	/**
	 * 正则表达式
	 */
	private static String compareRegex="(//$//.[A-Za-z0-9]+)=([//w]+)";
	private static String depKeyRegex="([/A-Za-z0-9]+):(//$//.[A-Za-z0-9]+)";
	private static String reqDataRegex="([/A-Za-z0-9]+:[//$//.A-Za-z0-9]+))";
	private static Map<String, String> map = new HashMap<String, String>();
	
	/**
	 * 建立正则规范
	 */
	private static Matcher getmatcher(String regex,String data){
		Matcher matcher = null;
		try {
			Pattern pattern = Pattern.compile(regex);
			matcher = pattern.matcher(data);
			
		} catch (Exception e) {
		}
		return matcher;
		
	}
	
	/**
	 * 预期值和实际值做对比
	 */
	
	public static void compareToReport(String actResult,String expResult){
		
		try {
			Matcher matcher = getmatcher(compareRegex, expResult);
			while (matcher.find()) {
				Assert.assertEquals(expResult, JSONPath.read(actResult, matcher.group(1).toString()),matcher.group(2));
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static int compareToDb(String expResult,String actResult){
		int flag = 0;
		List<Integer> list = new ArrayList<Integer>();
		try {
			Matcher matcher = getmatcher(compareRegex, expResult);
			while (matcher.find()) {
				int status = JSONPath.read(actResult, matcher.group(1)).toString().equals(matcher.group(2))?1:0;
				list.add(status);
				if (!list.contains(0)) {
					flag = 1;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
		
	}
	
	public static void StoreResponseValue (String depKey,String actResult){
		
		try {
			Matcher matcher = getmatcher(depKeyRegex, depKey);
			while (matcher.find()) {
				String jsonpath = matcher.group(1);
				String value = JSONPath.read(actResult, jsonpath).toString();
				map.put(matcher.group(), value);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	/**
	 * 处理接口依赖
	 */
	public static String handlerReqDataOfDep(String reqData){
	
		try {
			Matcher matcher =  getmatcher(reqDataRegex, reqData);
			while (matcher.find()) {
				String value = map.get(matcher.group(2));
				reqData = StringUtil.replaceStr(reqData, matcher.group(), value);
			}
		} catch (Exception e) {
			
		}
		return reqData;
		
	}
	
	
	
}
