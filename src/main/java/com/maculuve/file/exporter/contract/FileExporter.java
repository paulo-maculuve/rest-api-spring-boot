package com.maculuve.file.exporter.contract;

import java.util.List;

import org.springframework.core.io.Resource;

import com.maculuve.data.dto.v1.PersonDTO;

public interface FileExporter {
    Resource exportFile(List<PersonDTO> people) throws Exception;
}
