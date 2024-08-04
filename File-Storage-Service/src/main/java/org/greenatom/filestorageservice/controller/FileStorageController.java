package org.greenatom.filestorageservice.controller;

import lombok.RequiredArgsConstructor;
import org.greenatom.filestorageservice.dto.FileInfo;
import org.greenatom.filestorageservice.dto.FileId;
import org.greenatom.filestorageservice.entity.FileStorageEntity;
import org.greenatom.filestorageservice.response.ExceptionResponse;
import org.greenatom.filestorageservice.service.FileStorageService;
import org.greenatom.filestorageservice.util.FileNotFoundException;
import org.greenatom.filestorageservice.util.SortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
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
    public ResponseEntity<FileInfo> getFile(@PathVariable Long id) throws FileNotFoundException {
        logger.info("Request to get file {}", id);
        return ResponseEntity.ok(fileStorageService.getFile(id));
    }

    @GetMapping("/files/{page}")
    public ResponseEntity<Page<FileStorageEntity>> getSortedFeedPage(
            @PathVariable int page,
            @RequestParam(value = "sort", required = false, defaultValue = "UNSORTED") String sort
    ) {
        SortType sortType;
        try {
            sortType = SortType.valueOf(sort.toUpperCase());
        } catch (IllegalArgumentException e) {
            sortType = SortType.UNSORTED;
        }
        logger.info("Request to get feed page {} and Sort Type {}", page, sortType);
        return ResponseEntity.ok(fileStorageService.getFeedPage(page - 1, sortType));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        logger.error("Exception: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getMessage()));
    }

}
