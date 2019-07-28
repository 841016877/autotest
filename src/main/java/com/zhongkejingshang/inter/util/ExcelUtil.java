package com.zhongkejingshang.inter.util;

import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	private String filePath="";
	/**
	 * 创建构造方法
	 * 
	 */
	public ExcelUtil(String filepath ){
		this.filePath=filepath;
	}
	
	/**
	 * 得到workbook对象
	 */
	
	public Workbook getWb(){
		Workbook wb = null;
		try {
			//读取文件
			InputStream inputstream = new FileInputStream(filePath);
			//判断文件格式
			if (filePath.endsWith(".xlsx")) {
				wb = new XSSFWorkbook(inputstream);
			}else {
				wb = new HSSFWorkbook(inputstream);
			}
		} catch (Exception e) {
			
		}
		return wb;
	}
	
	/**
	 * 得到sheet对象
	 * @param sheetNum
	 * @return
	 */
	
	public Sheet getsheet(int sheetNum){
		Sheet sheet = null;
		try {
			Workbook wb = getWb();
			sheet = wb.getSheetAt(sheetNum);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return sheet;
		
	}
	
	/**
	 * 得到excel行和列的值
	 */
	public Object[][] getArrayCellValue(int sheetNum){
		Object[][] testCase = null;
		try {
			int LastRowNum = getsheet(sheetNum).getLastRowNum();
			testCase = new Object[LastRowNum][10];
			for (int rowIndex = 1; rowIndex <= LastRowNum; rowIndex++) {
				Row row = getsheet(sheetNum).getRow(rowIndex);
				if (row==null) {
					continue;
				}
				for (int cellIndex = 0; cellIndex <= row.getLastCellNum(); cellIndex++) {
					Cell cell = row.getCell(cellIndex);
					if (cell==null) {
						testCase[rowIndex-1][cellIndex]="";
					}else {
						testCase[rowIndex-1][cellIndex]=getCellValue(cell);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return testCase;
		
	}
	
	/**
	 * 得到列的值
	 */
	public Object getCellValue(Cell cell){
		Object value = null;
		try {
			if (cell.getCellType()==cell.CELL_TYPE_BLANK) {
				value="";
			}else if (cell.getCellType()==cell.CELL_TYPE_BOOLEAN) {
				value = cell.getBooleanCellValue();
			}else if (cell.getCellType()==cell.CELL_TYPE_FORMULA) {
				value = cell.getCellFormula();
			}else if (cell.getCellType()==cell.CELL_TYPE_NUMERIC) {
				value = cell.getNumericCellValue();
			}else if (cell.getCellType()==cell.CELL_TYPE_STRING) {
				value = cell.getStringCellValue();
			}else {
				value = cell.getDateCellValue();
			}
		} catch (Exception e) {
		}
		return value;
		
	}
	
}
