package com.zhongkejingshang.inter.util;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.dbcp.BasicDataSource;
import com.zhongkejingshang.inter.model.AutoLog;

public class DbcpUtil {
	private static BasicDataSource dataSource = null;
	private static String sqlBatch="insert into autolog2(testCase,reqType,reqUrl,reqData,expResult,actResult,result,execTime) values (?,?,?,?,?,?,?,?)";
	static{
		
		try {
			if (dataSource==null) {
				dataSource = new BasicDataSource();
				dataSource.setDriverClassName("com.mysql.jdbc.Driver");
				dataSource.setUrl("jdbc:mysql://182.61.31.93:3306/interface?characterEncoding=UTF-8");
				dataSource.setUsername("root");
				dataSource.setPassword("123456");
				
				dataSource.setInitialSize(20);
				dataSource.setMaxActive(20);
				dataSource.setMaxIdle(20);
				dataSource.setMinIdle(20);
				
				dataSource.setTestOnBorrow(false);
				dataSource.setTestOnReturn(false);
				dataSource.setMaxWait(2000);
				
				dataSource.setPoolPreparedStatements(true);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 建立连接
	 */
	public static Connection getconn(){
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return conn;
		
	}
	
	/**
	 * 基于jdbc进行增删改
	 */
	public static int jdbcUpDate(AutoLog autolog){
		int result = 0;
		try {
			Connection conn = getconn();
			PreparedStatement ps = conn.prepareStatement(sqlBatch);
			ps.setInt(1, autolog.getId());
			ps.setString(2, autolog.getReqType());
			result = ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
		
	}
	
	/**
	 * 基于jdbc批量进行增删改
	 */
	
	public static int[] jdbcUpdate(List<AutoLog> list){
		int[] result = null;
		try {
			Connection conn = getconn();
			System.out.println("数据库连接成功");
			PreparedStatement ps = conn.prepareStatement(sqlBatch);
			for (AutoLog autolog : list) {
				ps.setString(1, autolog.getTestcase());
				ps.setString(2, autolog.getReqType());
				ps.setString(3, autolog.getReqUrl());
				ps.setString(4, autolog.getReqData());
				ps.setString(5, autolog.getExpResult());
				ps.setString(6, autolog.getActResult());
				ps.setInt(7, autolog.getResult());
				ps.setString(8, autolog.getExectime());
				ps.addBatch();
			}
			result = ps.executeBatch();
			System.out.println("插入数据结果："+ Arrays.toString(result));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
		
	}
	
	
	/**
	 * 基于jdbc进行批量查询
	 */
	
	
	public static List<Object> jdbcQuery(AutoLog autolog,String sql){
		List<Object> list = null;
		try {
			Connection conn = getconn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, autolog.getId());
			ps.setString(2, autolog.getTestcase());
			ResultSet rs = ps.executeQuery();
			list = handlerValue(rs, AutoLog.class);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
		
	}
	
	
	public static List<Object> handlerValue(ResultSet rs,Class<?> cls){
		List<Object> list = new ArrayList<Object>();
		Object object = null;
		try {
			object = cls.newInstance();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				String columnName = rsmd.getColumnName(columnIndex);
				Field field = object.getClass().getDeclaredField(columnName);
				
				field.setAccessible(true);
				field.set(object, rs.getObject(columnIndex));
				
			}
			list.add(object);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		return list;
		
	}
	public static void main(String[] args){
		String insertsql="insert into autolog2(testCase,reqType,reqUrl,reqData,expResult,actResult,result,execTime) values(?,?,?,?,?,?,?,?)";
		List<AutoLog> list = new ArrayList<AutoLog>();
		AutoLog autolog = new AutoLog();
		autolog.setTestcase("zz");
		autolog.setReqType("zz");
		autolog.setReqUrl("zz");
		autolog.setReqData("zz");
		autolog.setExpResult("zz");
		autolog.setActResult("zz");
		autolog.setResult(0);
		autolog.setExectime("zz");
		list.add(autolog);
		System.out.println(list);
		int[] rowConut = jdbcUpdate(list);
		System.out.println(rowConut);
	
	
	}
}
