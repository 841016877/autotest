package com.youceedu.interf.util;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParseXmlUtil {
	
	//存放请求参数
	private static Map<String,String> reqConfig = new HashMap<String, String>();
	//存放dbcp连接数据库的参数
	private static Map<String,String> dbcpConfig = new HashMap<String, String>();
	//存放header参数
	private static Map<String,String> headerConfig = new HashMap<String, String>();
	
	//存放excel参数
	private static Map<String,String> excelConfig = new HashMap<String, String>();
		
	/**
	 * 空构造方法
	 */
	public ParseXmlUtil(){	
	}
	
	/**
	 * 有参数构造方法
	 */
	@SuppressWarnings("unchecked")
	public ParseXmlUtil(String xmlFilePath){
		try {
			SAXReader reader = new SAXReader();
			//得到XML文档
			Document document = reader.read(xmlFilePath);
			//得到根元素
			Element rootElement = document.getRootElement();
			
			//把XML中reqConfig元素的参数配置添加到reqConfig Map中
			List<Element> reqElements = rootElement.element("reqConfig").elements("param");
			for(Element req:reqElements){
				reqConfig.put(req.attributeValue("name").trim(), req.attributeValue("value").trim());
			}
			
			//把XML中headerConfig元素的参数配置添加到headerConfig Map中
			List<Element> headerElements = rootElement.element("headerConfig").elements("param");
			for(Element header:headerElements){
				headerConfig.put(header.attributeValue("name").trim(), header.attributeValue("value").trim());
			}

			//把XML中dbcpConfig元素的参数配置添加到dbcpConfig Map中
			List<Element> dbcpElements = rootElement.element("dbcpConfig").elements("param");
			for(Element dbcp:dbcpElements){
				dbcpConfig.put(dbcp.attributeValue("name").trim(), dbcp.attributeValue("value").trim());
			}
			
			//把XML中excelConfig元素的参数配置添加到excelConfig Map中
			List<Element> excelElements = rootElement.element("excelConfig").elements("param");
			for(Element excel:excelElements){
				excelConfig.put(excel.attributeValue("name").trim(), excel.attributeValue("value").trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @Title: getReqConfig   
	 * @Description: reqConfig存放请求参数
	 * @param: @return      
	 * @return: Map<String,String>      
	 * @throws
	 */
	public Map<String, String> getReqConfig(){
		return reqConfig;
	}
	
	/**
	 * @Title: getDbcpConfig   
	 * @Description: dbcpConfig存放dbcp连接数据库的参数
	 * @param: @return      
	 * @return: Map<String,String>      
	 * @throws
	 */
	public Map<String, String> getDbcpConfig(){
		return dbcpConfig;
	}
	
	/**
	 * @Title: getHeaderConfig   
	 * @Description: headerConfig存放请求header参数
	 * @param: @return      
	 * @return: Map<String,String>      
	 * @throws
	 */
	public Map<String, String> getHeaderConfig(){
		return headerConfig;
	}
	
	/**
	 * @Title: getExcelConfig   
	 * @Description: excelConfig存放excel参数
	 * @param: @return      
	 * @return: Map<String,String>      
	 * @throws
	 */
	public Map<String, String> getExcelConfig(){
		return excelConfig;
	}
	
	
	public static void main(String[] args) {
		ParseXmlUtil parseXmlUtil = new ParseXmlUtil("api-config.xml");
		String tmp = parseXmlUtil.getReqConfig().get("reqTimeout");
		System.out.println(tmp);
	}

}
