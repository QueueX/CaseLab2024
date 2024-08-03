package org.greenatom.filestorageservice.controller;

import lombok.RequiredArgsConstructor;
import org.greenatom.filestorageservice.dto.FileInfo;
import org.greenatom.filestorageservice.dto.FileId;
import org.greenatom.filestorageservice.response.ExceptionResponse;
import org.greenatom.filestorageservice.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/file-storage")
@RequiredArgsConstructor
public class FileStorageController {
    private final Logger logger = LoggerFactory.getLogger(FileStorageController.class);

    private final FileStorageService fileStorageService;

    @PostMapping("/create")
    public ResponseEntity<FileId> createFile(@RequestBody FileInfo request) {
        logger.info("Request to File Creating");
        return ResponseEntity.ok(fileStorageService.createFile(request));
    }

    @GetMapping("/file/id{id}")
    public ResponseEntity<FileInfo> getFile(@PathVariable Long id) {
        logger.info("Request to get file {}", id);
        return ResponseEntity.ok(fileStorageService.getFile(id));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(RuntimeException ex) {
        logger.error("Exception: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getMessage()));
    }

}
