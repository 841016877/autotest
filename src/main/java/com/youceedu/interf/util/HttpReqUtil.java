package com.youceedu.interf.util;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @ClassName:  HttpReqUtil   
 * @Description: 发送http请求  
 * @author: wangyanzhao 
 * @date:   2019年1月26日 下午6:59:29   
 *     
 * @Copyright: 2019 www.youceedu.com All rights reserved. 
 * 注意：本内容仅限于优测教育内部传阅，禁止外泄以及用于其他的商业目
 */
public class HttpReqUtil {
	
	/**
	 * 初始化
	 */
	private static BasicCookieStore cookieStore = new BasicCookieStore();
	
	/**
	 * @Title: httpReqConfig   
	 * @Description: 请求超时配置
	 * @param: @param httpRequestBase
	 * @param: @param param      
	 * @return: void      
	 * @throws
	 */
	public static void httpReqConfig(HttpRequestBase httpRequestBase,String param){
		//添加header
		httpRequestBase.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
		if(new ParseJsonToMapUtil().isJsonString(param)){
			httpRequestBase.setHeader("Content-Type","application/json; charset=UTF-8");
		}else{
			httpRequestBase.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		}
		
		//设置请求超时配置
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(2000)
				.build();
		
		httpRequestBase.setConfig(requestConfig);
	}
	
	/**
	 * @Title: sendGet   
	 * @Description: 发送get请求
	 * @param: @param url
	 * @param: @param param
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String sendGet(String url,String param){
		
		//初始化
		String result = null;
		CloseableHttpResponse response = null;
		String reqUrl = url + "?" + param;
		
		//得到httpclient,httpGet对象并自动管理cookie
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore)
				.build();
		
		HttpGet httpGet = new HttpGet(reqUrl);
		
		//添加请求配置
		httpReqConfig(httpGet,param);
		
		try {
			//发送请求及得到服务器返回值
			response = httpclient.execute(httpGet);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	/**
	 * @Title: sendPost   
	 * @Description: 发送post请求
	 * @param: @param url
	 * @param: @param param
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String sendPost(String url,String param){
		//初始化
		String result = null;
		CloseableHttpResponse response = null;
		
		//得到httpPost对象并自动管理cookie
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore)
				.build();
		
		
		HttpPost httpPost = new HttpPost(url);
		
		//添加请求配置
		httpReqConfig(httpPost,param);
		
		try {
			//设置消息体
			StringEntity stringEntity = new StringEntity(param);
			httpPost.setEntity(stringEntity);
			
			//发送请求及得到服务器返回值
			response = httpclient.execute(httpPost);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		/*
		//案例1
		String url_a = "http://www.nuandao.com/public/lazyentrance";
		String param_a = "isajax=1&remember=1&email=asfasfasfasdf@qq.com&password=3333333&agreeterms=1&itype=&book=1&m=0.12248663395993764";
		String tmp = sendPost(url_a,param_a);
		System.out.println(tmp);
		
		//案例2
		String url_b = "http://www.nuandao.com/shopping/cart";
		String param_b = "countdown=1&m=0.6105540007303787";
		String tmp2 = sendPost(url_b,param_b);
		System.out.println(tmp2);
		*/
		
		String url_b = "http://localhost:8080/aop-choose-db-demo/opt/set.html";
		String param_b = "key=name&value=1";
		String tmp2 = sendPost(url_b,param_b);
		System.out.println(tmp2);
		
	}
}
