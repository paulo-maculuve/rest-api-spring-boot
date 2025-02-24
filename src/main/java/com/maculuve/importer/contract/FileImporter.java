package com.maculuve.importer.contract;

import java.io.InputStream;
import java.util.List;

import com.maculuve.data.dto.v1.PersonDTO;

public interface FileImporter {
    List<PersonDTO> importFile(InputStream inputStream) throws Exception;
}
