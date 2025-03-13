package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    private String filePath;

    public ExcelReader(String filePath) {
        this.filePath = filePath;
    }

    public List<List<String>> readExcelData(String sheetName)
    {
        List<List<String>> data = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }

            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    cell.setCellType(CellType.STRING);
                    rowData.add(cell.getStringCellValue());
                }
                data.add(rowData);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read Excel file: " + filePath);
        }

        return data;
    }
}
