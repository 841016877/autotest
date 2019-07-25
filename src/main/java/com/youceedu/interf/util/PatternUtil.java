package com.youceedu.interf.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.testng.Assert;
import com.alibaba.fastjson.JSONPath;

public class PatternUtil {
	
	/**
	 * 定义正则表达式
	 */
	private static String compareResultRegex = "([\\$\\.a-zA-Z0-9]+)=(\\w+)";
	private static String depKeyRegex = "([/a-zA-Z0-9]+):([\\$\\.a-zA-Z0-9]+)";
	private static String reqDataRegex = "([/a-zA-Z0-9]+:[\\$\\.a-zA-Z0-9]+)";
	private static String functionRegex  = "\\$\\{__(\\w+)(\\([\\w\\,]*\\))\\}";
	private static Map<String,String> map = new HashMap<String,String>();
	
	/**
	 * @Title: getMatcher   
	 * @Description: TODO
	 * @param: @param regex
	 * @param: @param data
	 * @param: @return      
	 * @return: Matcher      
	 * @throws
	 */
	public static Matcher getMatcher(String regex,String data){
		//初始化返回值
		Pattern pattern = null;
		Matcher matcher = null;
		
		try{
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(data);
		}catch(Exception e){
			e.printStackTrace();
		}
		return matcher;
	}

	/**
	 * @Title: compareResultToReportng   
	 * @Description: 预期值和实际值对比
	 * @param: @param expResult
	 * @param: @param actResult      
	 * @return: void      
	 * @throws
	 */
	public static void compareResultToReportng(String expResult,String actResult){

		Matcher matcher = getMatcher(compareResultRegex,expResult);
		while (matcher.find()){
			Assert.assertEquals(JSONPath.read(actResult, matcher.group(1)).toString(), matcher.group(2));
		}
	}
	
	/**
	 * @Title: compareResultToDb   
	 * @Description: TODO
	 * @param: @param expResult
	 * @param: @param actResult
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	public static int compareResultToDb(String expResult,String actResult){
		
		//初始化返回值
		int flag = 0;
		List<Integer> list = new ArrayList<Integer>();
		
		Matcher matcher = getMatcher(compareResultRegex,expResult);
		while (matcher.find()){
			int status = JSONPath.read(actResult, matcher.group(1)).toString().equals(matcher.group(2)) ? 1:0;
			list.add(status);
		}
		
		if(!list.contains(0)){
			flag = 1;
		}
		
		return flag;
	}
	
	/**
	 * @Title: storeResponseValue   
	 * @Description: TODO
	 * @param: @param depKey
	 * @param: @param actResult      
	 * @return: void      
	 * @throws
	 */
	public static void storeResponseValue(String depKey,String actResult){
		
		Matcher matcher = getMatcher(depKeyRegex,depKey);
		while (matcher.find()){
			 String jsonPath = matcher.group(2);
			 String value = JSONPath.read(actResult, jsonPath).toString();
			 map.put(matcher.group(), value);
		}
	}
	
	/**
	 * @Title: handlerReqDataOfDep   
	 * @Description: TODO
	 * @param: @param reqData
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String handlerReqDataOfDep(String reqData){
		Matcher  matcher = getMatcher(reqDataRegex,reqData);
		while (matcher.find()){
			String value = map.get(matcher.group());
			reqData = StringUtil.replaceStr(reqData, matcher.group(), value);
		}
		return reqData;
	}
	
	/**
	 * @Title: handlerReqDataOfFunc   
	 * @Description: TODO
	 * @param: @param reqData
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String handlerReqDataOfFunc(String reqData){

		Matcher  matcher = getMatcher(functionRegex,reqData);
		while (matcher.find()){
			String funName = matcher.group(1);//Random
			String funParam = matcher.group(2).replace("(", "").replace(")", "");
			
			//得到函数对应的类处理结果
			String value = FunctionUtil.getValue(funName, funParam.split(","));
			
			//替换值
			reqData =StringUtil.replaceStr(reqData, matcher.group(), value);
		}
		return reqData;
	}

	public static void main(String[] args) {

	}

}
