package com.youceedu.interf.test;

import org.apache.http.util.TextUtils;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONPath;
import com.youceedu.interf.model.AutoLog;
import com.youceedu.interf.util.DateTimeUtil;
import com.youceedu.interf.util.DbcpUtil;
import com.youceedu.interf.util.ExcelUtil;
import com.youceedu.interf.util.HttpReqUtil;
import com.youceedu.interf.util.PatternUtil;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class TestRun {
	
  /*
   * 初始化
   */
  private String filePath = "/Users/zhangyonghui/Downloads/app_testcase1.xlsx";
  private static List<AutoLog> list = new ArrayList<AutoLog>();
	
  @Parameters({"filePathParam"})
  @BeforeTest
  public void beforeTest(String fromTestngXMLParam) {
	  if (!TextUtils.isEmpty(fromTestngXMLParam)) {
		  this.filePath = fromTestngXMLParam;
	  }
  }
	
  @Test(dataProvider = "testCaseData")
  public void httpReq(String id,String isExec,String testCase,String reqType,String reqHost,String reqInterface,String reqData,String expResult,String isDep,String depKey) {
	  
	  //初始化
	  String actResult = "";
	  String reqUrl = reqHost + reqInterface;
	  
	  //判断reqData是否有依赖,有则需从全局Map中取值
	  reqData = PatternUtil.handlerReqDataOfDep(reqData);
	  
	  //reqData-参数动态处理
	  reqData = PatternUtil.handlerReqDataOfFunc(reqData);
	  
	  //打印日志
	  Reporter.log("请求接口:"+reqUrl);
	  Reporter.log("请求参数:"+reqData);
	  Reporter.log("接口预期值:"+expResult);
	  
	  //发送请求-->得到服务器返回值
	  //执行用例
	  if("YES".equals(isExec)){
		  if("GET".equals(reqType)){
			  //发送get请求
			  actResult = HttpReqUtil.sendGet(reqUrl, reqData);
		  }else{
			  //发送post请求
			  actResult = HttpReqUtil.sendPost(reqUrl, reqData);
		  }
	  }else{
		  System.out.println("此接口不执行");
	  }
	  
	  //打印日志
	  Reporter.log("接口实际返回值:"+actResult);
	  
	  //接口返回值被依赖,把被依赖的key对应的value进行存储
	  if("YES".equals(isDep)){
		  PatternUtil.storeResponseValue(depKey,actResult);
	  }
	  
	  //收集测试结果数据
	  int result = PatternUtil.compareResultToDb(expResult, actResult);
	  list.add(new AutoLog(testCase,reqType,reqUrl,reqData,expResult,actResult,result,DateTimeUtil.getDateTime()));
	  
	  //预期值和实际值对比
	  PatternUtil.compareResultToReportng(expResult, actResult);
  }
  
  
  @DataProvider(name="testCaseData")
  public Object[][] dp() {
	  
	  //初始化返回值
	  Object[][] data = null;
	  try{
		  //得到excel内所有数据并存储在data中
		  ExcelUtil excelUtil = new ExcelUtil(filePath);
		  data = excelUtil.getArrayCellValue(0);
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	  return data;
  }
  
  @AfterTest
  public void afterTest(){
	//测试结果入库
	DbcpUtil.dbcpBatchUpdate(list);
  }
  
}
