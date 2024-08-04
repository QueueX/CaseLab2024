package org.greenatom.filestorageservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "File Storage Controller",
        description = "Основной контроллер микросервиса для работы с файлами"
)
@RequiredArgsConstructor
public class FileStorageController {
    private final Logger logger = LoggerFactory.getLogger(FileStorageController.class);

    private final FileStorageService fileStorageService;

    @PostMapping("/create")
    @Operation(
            summary = "Создание файла",
            description = "Метод предназначен для создания файла и отправки его в базу данных"
    )
    public ResponseEntity<FileId> createFile(@RequestBody @Parameter(description = "Файл и его атрибуты") FileInfo request) {
        logger.info("Request to File Creating");
        return ResponseEntity.ok(fileStorageService.createFile(request));
    }

    @GetMapping("/file/id{id}")
    @Operation(
            summary = "Получение файла по id",
            description = "Метод предназначен получения файла и его атрибутов по id"
    )
    public ResponseEntity<FileInfo> getFile(@PathVariable @Parameter(description = "id файла") Long id) {
        logger.info("Request to get file {}", id);
        return ResponseEntity.ok(fileStorageService.getFile(id));
    }

    @GetMapping("/files/{page}")
    @Operation(
            summary = "Получение ленты с файлами",
            description = "Метод предназначен для получения страницы, состоящей из 10 файлов. " +
                    "Также в зависимости от аргумента в строке позволяет сортировать файлы по дате создания"
    )
    public ResponseEntity<Page<FileStorageEntity>> getSortedFeedPage(
            @PathVariable @Parameter(description = "Номер страницы") int page,
            @RequestParam(value = "sort", required = false, defaultValue = "UNSORTED")
            @Parameter(description = "Способ сортировки") String sort
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
