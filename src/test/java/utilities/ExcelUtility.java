package utilities;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
 
public class ExcelUtility {	


	public static void write(String sheetName, int rownum, int colnum, String data)throws IOException {

		//Opening the excel file
		FileInputStream file=new FileInputStream(System.getProperty("user.dir")+"\\TestData\\ExcelFile.xlsx");

		XSSFWorkbook book =new XSSFWorkbook(file);

		//Creating the sheet if it does not exist
		if(book.getSheetIndex(sheetName)==-1) 
		{
			book.createSheet(sheetName);              

		}

		//creating Sheet
		XSSFSheet sheet=book.getSheet(sheetName);

		//creating Row
		if(sheet.getRow(rownum)==null) 
		{
			sheet.createRow(rownum);
		}

		XSSFRow row =sheet.getRow(rownum);   

		// Creating cell
		XSSFCell cell=row.createCell(colnum);

		//Setting the data in the column
		cell.setCellValue(data);

		FileOutputStream fo=new FileOutputStream(System.getProperty("user.dir")+"\\TestData\\ExcelFile.xlsx");

		book.write(fo);
		book.close();
		file.close();
		fo.flush();
		fo.close();

	}
	
	public static void clearExcel(String sheetName) throws IOException {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\TestData\\ExcelFile.xlsx");
		XSSFWorkbook work = new XSSFWorkbook(fis);
		fis.close();
		
		if(work.getSheetIndex(sheetName) != -1) {
			XSSFSheet sheet = work.getSheet(sheetName);
			while(sheet.getLastRowNum() >= 0) {
				 XSSFRow row = sheet.getRow(sheet.getLastRowNum());
				sheet.removeRow(row);
			}
			FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir")+"\\TestData\\ExcelFile.xlsx");
			work.write(fos);
			fos.close();
			work.close();
		}
	}
}