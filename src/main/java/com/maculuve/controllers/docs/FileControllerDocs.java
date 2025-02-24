package com.maculuve.controllers.docs;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.maculuve.data.dto.v1.UploadFIleResponseDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "File Endpoint")
public interface FileControllerDocs {
    UploadFIleResponseDTO uploadFile(MultipartFile file);

    List<UploadFIleResponseDTO> uploadFiles(MultipartFile[] files);

    ResponseEntity<Resource> donwloadFile(String fileName, HttpServletRequest httpServletRequest);
}
