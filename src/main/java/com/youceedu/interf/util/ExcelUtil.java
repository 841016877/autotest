package com.youceedu.interf.util;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @ClassName:  ExcelUtil   
 * @Description:TODO   
 * @author: wangyanzhao 
 * @date:   2019年1月6日 下午6:30:12   
 *     
 * @Copyright: 2019 www.youceedu.com All rights reserved. 
 * 注意：本内容仅限于优测教育内部传阅，禁止外泄以及用于其他的商业目
 */
public class ExcelUtil {
	
	/**
	 * 初始化
	 */
	private  String fileName = null;
	
	/**
	 * @Title:  Test   
	 * @Description:    TODO 
	 * @param:  @param fileName  
	 * @throws
	 */
	public ExcelUtil(String fileName){
		this.fileName = fileName;
	}
	
	/**
	 * @Title: getWb   
	 * @Description: TODO
	 * @param: @return      
	 * @return: Workbook      
	 * @throws
	 */
	public  Workbook getWb(){
		
		//初始化返回值
		Workbook wb = null;
		
		
		try{
			InputStream inputStream = new FileInputStream(fileName);
			
			//依据文件后缀判断实例化类型
			if(fileName.endsWith(".xlsx")){
				wb = new XSSFWorkbook(inputStream);
			}else{
				wb =new HSSFWorkbook(inputStream);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return wb;
	}
	
	/**
	 * @Title: getSheet   
	 * @Description: TODO
	 * @param: @param index
	 * @param: @return      
	 * @return: Sheet      
	 * @throws
	 */
	public  Sheet getSheet(int index){
		//初始化返回值
		Sheet sheet = null;
		
		try{
			Workbook wb = getWb();
			//依据sheet下标得到sheet对象
			sheet = wb.getSheetAt(index);
		}catch(Exception e){
			e.printStackTrace();
		}

		return sheet;
	}
	
	/**
	 * @Title: getCellValue   
	 * @Description: TODO
	 * @param: @param index
	 * @param: @param rownum
	 * @param: @param cellnum
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	public Object getCellValue(int index,int rownum,int cellnum){
		
		//初始化返回值
		Object result = null;
		
		try{
			//依据sheet,行,列下标,得到单元格值
			Row row = getSheet(index).getRow(rownum);
			Cell cell = row.getCell(cellnum);
			result = fromCellTypeGetCellValue(cell);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * @Title: fromCellTypeGetCellValue   
	 * @Description: TODO
	 * @param: @param cell
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	public  Object fromCellTypeGetCellValue(Cell cell){
		
		//初始化返回值
		Object value = null;
		
		try{
			//整个代码块依据单元格类型取对应的值
			if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
		    	   value = "";
		    	   
			}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
				value = cell.getNumericCellValue();
		    	   
			}else if(cell.getCellType()==Cell.CELL_TYPE_STRING){
				value = cell.getStringCellValue().trim();

			}else if(cell.getCellType()==Cell.CELL_TYPE_FORMULA){
				value = cell.getCellFormula();
				
			}else if(cell.getCellType()==Cell.CELL_TYPE_BOOLEAN){
				value = cell.getBooleanCellValue();
	 	   
			} else if(cell.getCellType()==Cell.CELL_TYPE_ERROR){
		    	   value = "";
			} else {
				value = cell.getDateCellValue();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return value;
	}
	
	/**
	 * @Title: getArrayCellValue   
	 * @Description: TODO
	 * @param: @param sheetIndex
	 * @param: @return      
	 * @return: Object[][]      
	 * @throws
	 */
	public  Object[][] getArrayCellValue(int sheetIndex){
		
		//初始化返回值
		Object[][] testCaseData = null;
		
		try{
			//用例总行数
			int totalRowIndex = getSheet(sheetIndex).getLastRowNum();
			testCaseData  = new Object[totalRowIndex][10];

	        for(int rowIndex = 1; rowIndex <= totalRowIndex; rowIndex++){
	        	//通过sheet指定到rowIndex那行
	            Row row = getSheet(sheetIndex).getRow(rowIndex);
	            
	            //空行跳过
	            if(row == null){
	                continue;
	            }
	            
	            //指定到行后,遍历每列值
		        for(int cellIndex = 0;cellIndex < row.getLastCellNum();cellIndex++){
		        	
		            Cell cell = row.getCell(cellIndex);
		            if(cell == null){
		            	testCaseData[rowIndex -1][cellIndex]="";
		            }else{
		            	testCaseData[rowIndex -1][cellIndex] = fromCellTypeGetCellValue(cell);
		            }
		        }
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return testCaseData;
	}
	
	public static void main(String[] args) {
		//ExcelUtil tmp = new ExcelUtil("D:\\autotest\\app\\form\\app_testcase.xlsx");
		//Object object = tmp.getCellValue(0, 2, 2);
		//System.out.println(object.toString());
		
		ExcelUtil tmp = new ExcelUtil("D:\\autotest\\app\\form\\app_testcase.xlsx");
		Object[][] object = tmp.getArrayCellValue(0);
		System.out.println(object[1][1]);
		System.out.println(object[2][3]);
	}

}
