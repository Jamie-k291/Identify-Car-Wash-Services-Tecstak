package utilities;

import java.util.Hashtable;

public class TestDataProvider {

	/************** To the Data for TestCase ******************/
	public static Object[][] getTestData(String SheetName, String TestName) {

		ReadExcelDataFile readdata = new ReadExcelDataFile("src//test//Resources//TestData.xlsx");
		String sheetName = SheetName;
		String testName = TestName;

		int startRowNum = 0;
		while (!readdata.getCellData(sheetName, 0, startRowNum).equalsIgnoreCase(testName)) {
			startRowNum++;// Find Start Row of TestCase
		}

		int startTestColumn = startRowNum + 1;
		int startTestRow = startRowNum + 2;

		int rows = 0;
		while (!readdata.getCellData(sheetName, 0, startTestRow + rows).equals("")) {
			rows++;// Find Number of Rows of TestCase
		}

		int colmns = 0;
		while (!readdata.getCellData(sheetName, colmns, startTestColumn).equals("")) {
			colmns++;// Find Number of Columns in Test
		}

		// Define Two Object Array
		Object[][] dataSet = new Object[rows][1];
		Hashtable<String, String> dataTable = null;
		int dataRowNumber = 0;
		for (int rowNumber = startTestRow; rowNumber <= startTestColumn + rows; rowNumber++) {
			dataTable = new Hashtable<String, String>();
			for (int colNumber = 0; colNumber < colmns; colNumber++) {
				String key = readdata.getCellData(sheetName, colNumber, startTestColumn);
				String value = readdata.getCellData(sheetName, colNumber, rowNumber);
				dataTable.put(key, value);
			}
			dataSet[dataRowNumber][0] = dataTable;
			dataRowNumber++;
		}
		return dataSet;
	}
}
