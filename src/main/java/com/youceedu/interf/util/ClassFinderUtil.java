package com.youceedu.interf.util;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.lang.Class;
import com.youceedu.interf.functions.Function;
import com.youceedu.interf.functions.Md5Function;


public class ClassFinderUtil{
	
	/**
	 * @Title: getAllAssignedClass   
	 * @Description: 据类名可得到同级目录及子目录下此类的子类集合
	 * @param: @param cls
	 * @param: @return      
	 * @return: List<Class<?>>      
	 * @throws
	 */
	public static List<Class<?>> getAllAssignedClass(Class<?> cls) { 
		//定义返回值
        List<Class<?>> classes = new ArrayList<Class<?>>(); 
        
        for (Class<?> c : getClasses(cls)) {  
            if (cls.isAssignableFrom(c) && !cls.equals(c)) {
                classes.add(c);
            }  
        }  
        return classes;  
    }
	
	/**
	 * @Title: getClasses   
	 * @Description: 据File递归查找所有class
	 * @param: @param dir
	 * @param: @param pk
	 * @param: @return      
	 * @return: List<Class<?>>      
	 * @throws
	 */
	private static List<Class<?>> getClasses(File dir, String pk){
		//定义返回值
		List<Class<?>> classes = new ArrayList<Class<?>>();
		
		//按照目录进行逐层查找class类文件
        for (File f : dir.listFiles()){
            if (f.isDirectory()){
                getClasses(f, pk + "." + f.getName());
            }
            
            String name = f.getName();  
	        if (name.endsWith(".class")){
	        	 try{
	        		 classes.add(Class.forName(pk + "." + name.substring(0, name.length() - 6)));
	        	 }catch(Exception e){
	        		 e.printStackTrace();
	        	 }
	        }  
        }
        return classes;
    }
	
	/**
	 * @Title: getClasses   
	 * @Description: 据类名可得到此类同级目录及子目录下的所有类
	 * @param: @param cls
	 * @param: @return      
	 * @return: List<Class<?>>      
	 * @throws
	 */
	public static List<Class<?>> getClasses(Class<?> cls){
		//定义返回值
		List<Class<?>> classes = new ArrayList<Class<?>>();
		String pk = cls.getPackage().getName();
		String path = pk.replace('.', '/');
		
		try{
			 String dirPath = cls.getClassLoader().getResource(path).getPath();
			 classes = getClasses(new File(dirPath),pk);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}
 
	public static void main(String[] args){

	}

}
