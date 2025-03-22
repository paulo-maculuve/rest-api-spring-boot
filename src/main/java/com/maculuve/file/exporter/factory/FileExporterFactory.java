package com.maculuve.file.exporter.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.maculuve.exceptions.BadRequestException;
import com.maculuve.file.exporter.MediaTypes;
import com.maculuve.file.exporter.contract.FileExporter;
import com.maculuve.file.exporter.impl.CsvExporter;
import com.maculuve.file.exporter.impl.PdfExporter;
import com.maculuve.file.exporter.impl.XlsxExporter;

@Component
public class FileExporterFactory {

    @Autowired
    private ApplicationContext applicationContext;

    public FileExporter getExporter(String acceptHeader) throws Exception {
        if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)) {
            return applicationContext.getBean(XlsxExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)) {
            return applicationContext.getBean(CsvExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_PDF_VALUE)) {
            return applicationContext.getBean(PdfExporter.class);
        } else {
            throw new BadRequestException("Invalid File Format");
        }
    }

}
