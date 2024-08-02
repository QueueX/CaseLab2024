package org.greenatom.filestorageservice.controller;

import org.greenatom.filestorageservice.request.CreateFileRequest;
import org.greenatom.filestorageservice.response.CreateFileResponse;
import org.greenatom.filestorageservice.response.ExceptionResponse;
import org.greenatom.filestorageservice.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/file-storage")
public class FileStorageController {
    private final Logger logger = LoggerFactory.getLogger(FileStorageController.class);

    private final FileStorageService fileStorageService;

    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateFileResponse> createFile(@RequestBody CreateFileRequest request) {
        logger.info("Request to File Creating");
        return ResponseEntity.ok(fileStorageService.createFile(request));
    }

    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        logger.error("Exception: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getMessage()));
    }

}
