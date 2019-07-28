package com.zhongkejingshang.inter.util;

	import java.text.DateFormat;
	import java.text.SimpleDateFormat;
	import java.util.Date;

	public class DataTimeUtil {
		//初始化变量
		
		private String str = "yyyy:MM:dd HH:mm:ss:SSS";
		
		/**
		 * 获取时间戳
		 * @param args
		 */
		public  Long getTimeSteam(){
			return new Date().getTime();
			
		}
		/**
		 * 获取当前时间和日期
		 * @param args
		 */
		public static String getDateTime(){
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.format(new Date());
			
		}
		
		/**
		 * 获取当前时间和日期及毫秒
		 * @param args
		 */
		public String getDateComplete(){
			SimpleDateFormat sdf = new SimpleDateFormat(str);
			return sdf.format(new Date());
		}
		
		/**
		 * 获取当前的日期是date类型
		 * @param args
		 * @throws Exception 
		 */
		
		public Date getDateType(String strDate) throws Exception{
			SimpleDateFormat sdf = new SimpleDateFormat(str);
			return sdf.parse(strDate);
			
		}
		
		public static void main(String[] args) {
				System.out.println(getDateTime());

		}

	}

