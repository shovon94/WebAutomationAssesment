package tests;

import org.testng.annotations.Test;
import utils.ExcelReader;

import java.util.List;

public class ExcelTest {

    @Test
    public void testExcelReading() {
        String filePath = "src/test/resources/testData/TestData_v2.xlsx";  // Path to Excel file
        ExcelReader excelReader = new ExcelReader(filePath);
        List<List<String>> data = excelReader.readExcelData("LoginData");

        for (List<String> row : data) {
            System.out.println(row);
        }
    }
}
