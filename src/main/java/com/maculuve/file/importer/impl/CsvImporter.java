package com.maculuve.file.importer.impl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import com.maculuve.data.dto.v1.PersonDTO;
import com.maculuve.file.importer.contract.FileImporter;

@Component
public class CsvImporter implements FileImporter {

    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {
        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build();
        Iterable<CSVRecord> records = csvFormat.parse(new InputStreamReader(inputStream));
        return parseRecordsToPersonDTOs(records);
    }

    private List<PersonDTO> parseRecordsToPersonDTOs(Iterable<CSVRecord> records) {
        List<PersonDTO> people = new ArrayList<>();

        for (CSVRecord csvRecord : records) {
            PersonDTO personDTO = new PersonDTO();
            personDTO.setFirstName(csvRecord.get("first_name"));
            personDTO.setLastName(csvRecord.get("last_name"));
            personDTO.setAddress(csvRecord.get("address"));
            personDTO.setGender(csvRecord.get("gender"));
            personDTO.setEnabled(true);
            people.add(personDTO);
        }
        return people;
    }

}
