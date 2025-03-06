package com.maculuve.file.importer.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.maculuve.exceptions.BadRequestException;
import com.maculuve.file.importer.contract.FileImporter;
import com.maculuve.file.importer.impl.CsvImporter;
import com.maculuve.file.importer.impl.XlsxImporter;

@Component
public class FileImporterFactory {

    @Autowired
    private ApplicationContext applicationContext;

    public FileImporter getImporter
    (String fileName) throws Exception {
        if (fileName.endsWith(".xlsx")) {
            return applicationContext.getBean(XlsxImporter.class);
        } else if (fileName.endsWith(".csv")) {
            return applicationContext.getBean(CsvImporter.class);
        } else {
            throw new BadRequestException("Invalid File Format");
        }
    }



}
