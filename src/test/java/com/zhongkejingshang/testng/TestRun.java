package com.zhongkejingshang.testng;

import com.zhongkejingshang.inter.util.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.mysql.fabric.xmlrpc.base.Data;
import com.zhongkejingshang.inter.model.AutoLog;

import java.util.ArrayList;
import java.util.List;

import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.IFactoryAnnotation;

public class TestRun {

    private static String filePath = "/Users/zhangyonghui/Downloads/app_testcase1.xlsx";
    private static List<AutoLog> list = new ArrayList<AutoLog>();

    @Test(dataProvider = "dp")
    public void f(String id, String isExec, String testCase, String reqType, String reqHost, String reqInterface, String reqData, String expResult, String isDep, String depKey) {


        String reqUrl = reqHost + reqInterface;
        String actResult = null;

        //PatterenUtil.handlerReqDataOfDep(reqData);


        Reporter.log("接口请求地址：" + reqUrl);
        Reporter.log("请求参数：" + reqData);
        Reporter.log("请求预期值：" + expResult);


        if ("YES".equals(isExec)) {
            if ("GET".equals(reqType)) {
                actResult = HttpReqUtil.sendGet(reqUrl, reqData);
            } else {
                actResult = HttpReqUtil.sendPost(reqUrl, reqData);
            }
        } else {
            System.out.println("此接口不执行");
        }
        Reporter.log("接口实际返回值：" + reqData);
        //实际值和预期值做对比将测试结果入库
        int result = PatterenUtil.compareToDb(expResult, actResult);
        list.add(new AutoLog(testCase, reqType, reqUrl, reqData, expResult, actResult, result, DataTimeUtil.getDateTime()));
        Reporter.log("实际值和预期值做对比：" + result);
        Reporter.log("数据库数据：" + list);
        //预期值和实际值做对比
        PatterenUtil.compareToReport(actResult, expResult);


    }


    @DataProvider
    public Object[][] dp() {
        Object[][] testCase = null;
        try {
            ExcelUtil excelUtil = new ExcelUtil(filePath);
            testCase = excelUtil.getArrayCellValue(0);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return testCase;
    }

    @AfterTest
    public void afterTest() {
        //测试结果入库
        DbcpUtil.jdbcUpdate(list);
    }
}
