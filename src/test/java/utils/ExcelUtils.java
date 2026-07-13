package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook wb;
	public XSSFSheet sh;
	public XSSFRow row;
	public XSSFCell cell;
	public XSSFCellStyle style;
	public String xlfile;
	
	public ExcelUtils(String xlfile) {
		this.xlfile=xlfile;
	}
	
	public int getRowCount(String sheetname) throws IOException {
		fi=new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		sh=wb.getSheet(sheetname);
		int totalRows = sh.getLastRowNum();
		wb.close();
		fi.close();
		return totalRows;
	}
	
	public int getCellCount(String sheetname) throws IOException {
		fi=new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		sh=wb.getSheet(sheetname);
		int totalcells = sh.getRow(0).getLastCellNum();
		wb.close();
		fi.close();
		return totalcells;
	}
	
	public String getSpecificCellData(String sheetname, int rownum, int cellnum) throws IOException {
		fi=new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		sh=wb.getSheet(sheetname);
		cell = sh.getRow(rownum).getCell(cellnum);
		String cellvalue;
		try {
			DataFormatter df = new DataFormatter();
			cellvalue=df.formatCellValue(cell);	
		}catch(Exception e) {
			cellvalue="";			
		}
		return cellvalue;
	}
	
	public void setGreenColorCell(String sheetname, int rownum, int cellnum) throws IOException {
		fi=new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		sh=wb.getSheet(sheetname);
		cell = sh.getRow(rownum).getCell(cellnum);
		style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(style);
		fo=new FileOutputStream(xlfile);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
	}
	
	public void setRedColorCell(String sheetname, int rownum, int cellnum) throws IOException {
		fi=new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		sh=wb.getSheet(sheetname);
		cell = sh.getRow(rownum).getCell(cellnum);
		style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(style);
		fo=new FileOutputStream(xlfile);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
	}
}
