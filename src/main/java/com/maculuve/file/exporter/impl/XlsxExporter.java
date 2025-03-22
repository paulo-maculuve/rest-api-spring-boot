package com.maculuve.file.exporter.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.maculuve.data.dto.v1.PersonDTO;
import com.maculuve.file.exporter.contract.FileExporter;

@Component
public class XlsxExporter implements FileExporter {

    @Override
    public Resource exportFile(List<PersonDTO> people) throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("People");
            Row headerRow = sheet.createRow(0);
            String[] headers = { "ID", "Firt Name", "Last Name", "Address", "Gander", "Enabled" };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderCellStyle(workbook));

                int rowIndex = 1;
                for (PersonDTO person : people) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(person.getId());
                    row.createCell(1).setCellValue(person.getFirstName());
                    row.createCell(2).setCellValue(person.getLastName());
                    row.createCell(3).setCellValue(person.getAddress());
                    row.createCell(4).setCellValue(person.getGender());
                    row.createCell(5).setCellValue(person.getEnabled() != null && person.getEnabled() ? "Yes" : "No");
                }

            }
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            workbook.write(arrayOutputStream);
            return new ByteArrayResource(arrayOutputStream.toByteArray());
        }

    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

}
