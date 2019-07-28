package com.zhongkejingshang.inter.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpReqUtil {
	private static BasicCookieStore cookieStore = new BasicCookieStore();
	/**
	 * 创建get方法
	 * @param args
	 */
	
	public static String sendGet(String url,String param){
		CloseableHttpClient httpClient = null;
		String reqUrl = url+"?"+param;
		CloseableHttpResponse httpResponse = null;
		String result =null;
		
		try {
			//创建httpClient,httpget对象，并进行自动管理
			httpClient = HttpClients.custom()
			.setDefaultCookieStore(cookieStore)
			.build();
			
			HttpGet httpGet = new HttpGet(reqUrl);
			
			httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				result = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	/**
	 * 创建post方法
	 * @param args
	 */
	
	public static String sendPost(String url,String param){
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		String result =null;
		
		try {
			//创建httpClient,httpget对象，并进行自动管理
			httpClient = HttpClients.custom()
			.setDefaultCookieStore(cookieStore)
			.build();
			HttpPost httpPost = new HttpPost(url);
			
			StringEntity stringEntity = new StringEntity(param);
			httpPost.setEntity(stringEntity);
			
			httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				result = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	public static void main(String[] args) {
		

	}

}
