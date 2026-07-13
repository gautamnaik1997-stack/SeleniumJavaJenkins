package utils;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name="LoginData")
	public String[][] dataprovider() throws IOException {
		
		String file = System.getProperty("user.dir")+"//testData//OpenCart_LoginData.xlsx";
		ExcelUtils xlutils= new ExcelUtils(file);
		int totalrows = xlutils.getRowCount("Sheet1");
		int totalcells = xlutils.getCellCount("Sheet1");
		String loginData[][] = new String[totalrows][totalcells];
		for(int r=1; r<=totalrows; r++) {
			for(int c=0; c<totalcells; c++) {
				loginData[r-1][c] = xlutils.getSpecificCellData("Sheet1", r, c);
			}
		}
		return loginData;
	}

}
