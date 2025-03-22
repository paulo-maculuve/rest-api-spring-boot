package com.maculuve.file.exporter.impl;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.maculuve.data.dto.v1.PersonDTO;
import com.maculuve.file.exporter.contract.FileExporter;

@Component
public class CsvExporter implements FileExporter {

    @Override
    public Resource exportFile(List<PersonDTO> people) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader("ID", "Firt Name", "Last Name", "Address", "Gander", "Enabled")
                .setSkipHeaderRecord(false)
                .build();
        try (CSVPrinter csvPrinter = new CSVPrinter(outputStreamWriter, csvFormat)) {
            for (PersonDTO person : people) {
                csvPrinter.printRecord(
                        person.getId(),
                        person.getFirstName(),
                        person.getLastName(),
                        person.getAddress(),
                        person.getGender(),
                        person.getEnabled());
            }
        }

        return new ByteArrayResource(outputStream.toByteArray());
    }

}
