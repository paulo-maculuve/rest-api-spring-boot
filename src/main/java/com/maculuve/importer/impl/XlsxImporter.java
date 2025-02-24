package com.maculuve.importer.impl;

import java.io.InputStream;
import java.util.List;

import com.maculuve.data.dto.v1.PersonDTO;
import com.maculuve.importer.contract.FileImporter;

public class XlsxImporter implements FileImporter{

    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'importFile'");
    }
    
}
