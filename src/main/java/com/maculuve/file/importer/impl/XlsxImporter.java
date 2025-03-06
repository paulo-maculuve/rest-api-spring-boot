package com.maculuve.file.importer.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.maculuve.data.dto.v1.PersonDTO;
import com.maculuve.file.importer.contract.FileImporter;

@Component
public class XlsxImporter implements FileImporter {

    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {

        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            if (iterator.hasNext())
                iterator.next();
            return parseRowsToPersonDTOtoList(iterator);

        }
    }

    private List<PersonDTO> parseRowsToPersonDTOtoList(Iterator<Row> iterator) {
        List<PersonDTO> people = new ArrayList<>();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            if (isRowValid(row)) {
                people.add(parseRowToPersonDTO(row));
            }
        }
        return people;
    }

    private PersonDTO parseRowToPersonDTO(Row row) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName(row.getCell(0).getStringCellValue());
        personDTO.setLastName(row.getCell(1).getStringCellValue());
        personDTO.setAddress(row.getCell(2).getStringCellValue());
        personDTO.setGender(row.getCell(3).getStringCellValue());
        personDTO.setEnabled(true);
        return personDTO;
    }

    private boolean isRowValid(Row row) {
        return row.getCell(0) != null || row.getCell(0).getCellType() != CellType.BLANK;
    }

}
