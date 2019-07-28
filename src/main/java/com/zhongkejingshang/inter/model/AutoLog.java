package com.zhongkejingshang.inter.model;

import java.io.Serializable;


public class AutoLog implements Serializable{
	private int id ;
	private String testcase;
	private String reqType;
	private String reqUrl;
	private String reqData;
	private String expResult;
	private String actResult;
	private int result;
	private String exectime;
	
	public  AutoLog(){}
	
	public AutoLog(String testcase, String reqType, String reqUrl, String reqData, String expResult, String actResult,
			int result, String exectime) {
		
		this.testcase = testcase;
		this.reqType = reqType;
		this.reqUrl = reqUrl;
		this.reqData = reqData;
		this.expResult = expResult;
		this.actResult = actResult;
		this.result = result;
		this.exectime = exectime;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTestcase() {
		return testcase;
	}


	public void setTestcase(String testcase) {
		this.testcase = testcase;
	}


	public String getReqType() {
		return reqType;
	}


	public void setReqType(String reqType) {
		this.reqType = reqType;
	}


	public String getReqUrl() {
		return reqUrl;
	}


	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}


	public String getReqData() {
		return reqData;
	}


	public void setReqData(String reqData) {
		this.reqData = reqData;
	}


	public String getExpResult() {
		return expResult;
	}


	public void setExpResult(String expResult) {
		this.expResult = expResult;
	}


	public String getActResult() {
		return actResult;
	}


	public void setActResult(String actResult) {
		this.actResult = actResult;
	}


	public int getResult() {
		return result;
	}


	public void setResult(int result) {
		this.result = result;
	}


	public String getExectime() {
		return exectime;
	}


	public void setExectime(String exectime) {
		this.exectime = exectime;
	}
	
	
	

}
