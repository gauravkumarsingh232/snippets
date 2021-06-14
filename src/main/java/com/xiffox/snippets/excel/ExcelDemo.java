package com.xiffox.snippets.excel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelDemo {

    public static void main(String[] args) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet spreadsheet = wb.createSheet();
        FileOutputStream fos = null;

        for (int row = 0; row < 10; row++) {
            XSSFRow xssfRow = spreadsheet.createRow(row); // row should be created only once per iteration
            for (int col = 0; col < 4; col++) {
                XSSFCell value = xssfRow.createCell(col);
                value.setCellValue(row + " Test");
                // value.setCellValue("("+ row + "," + col + ") Test");

            }
        }

        fos = new FileOutputStream("D://Testexcel.xlsx"); // this should done at the end, not within the loop
        wb.write(fos);
        wb.close();
        fos.close();

    }
}