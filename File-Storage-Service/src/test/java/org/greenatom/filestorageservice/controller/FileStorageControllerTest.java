package org.greenatom.filestorageservice.controller;

import org.greenatom.filestorageservice.dto.FileId;
import org.greenatom.filestorageservice.dto.FileInfo;
import org.greenatom.filestorageservice.entity.FileStorageEntity;
import org.greenatom.filestorageservice.response.ExceptionResponse;
import org.greenatom.filestorageservice.service.FileStorageService;
import org.greenatom.filestorageservice.util.FileNotFoundException;
import org.greenatom.filestorageservice.util.SortType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FileStorageControllerTest {

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private FileStorageController fileStorageController;

    private final FileInfo fileInfo = new FileInfo(
            "title",
            new Date(1212121212121212L),
            "description",
            "file".getBytes()
    );

    private final FileStorageEntity entity = new FileStorageEntity(
            fileInfo.getTitle(),
            fileInfo.getCreationDate(),
            fileInfo.getDescription(),
            fileInfo.getFile()
    );

    private final Long id = 1L;

    private final FileId fileId = new FileId(id);

    @Test
    void createFile_validData_returnedValidFileId() {
        when(fileStorageService.createFile(fileInfo)).thenReturn(fileId);

        ResponseEntity<FileId> result = fileStorageController.createFile(fileInfo);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(fileId, result.getBody());
        verify(fileStorageService, times(1)).createFile(fileInfo);
    }

    @Test
    void getFile_fileExists_returnFileInfo() {
        when(fileStorageService.getFile(id)).thenReturn(fileInfo);

        ResponseEntity<FileInfo> result = fileStorageController.getFile(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(fileInfo, result.getBody());
        verify(fileStorageService, times(1)).getFile(id);
    }

    @Test
    void getFile_fileDoesNotExist_throwFileNotFoundException() {
        when(fileStorageService.getFile(id)).thenThrow(new FileNotFoundException());

        ResponseEntity<ExceptionResponse> result = fileStorageController.handleException(new FileNotFoundException());

        assertThrows(FileNotFoundException.class, () -> fileStorageService.getFile(id));
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(new ExceptionResponse(new FileNotFoundException().getMessage()), result.getBody());
        verify(fileStorageService, times(1)).getFile(id);
    }

    @Test
    void getSortedFeedPage_noSort_returnsUnsortedSlice() {
        Slice<FileStorageEntity> slice = new SliceImpl<>(Collections.singletonList(entity));
        when(fileStorageService.getFeedPage(0, SortType.UNSORTED)).thenReturn(slice);

        ResponseEntity<Slice<FileStorageEntity>> result = fileStorageController.getSortedFeedPage(1, "UNSORTED");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(slice, result.getBody());
        verify(fileStorageService, times(1)).getFeedPage(0, SortType.UNSORTED);
    }

    @Test
    void getSortedFeedPage_withSort_returnsSortedSlice() {
        Slice<FileStorageEntity> slice = new SliceImpl<>(Collections.singletonList(entity));
        when(fileStorageService.getFeedPage(0, SortType.ASC)).thenReturn(slice);

        ResponseEntity<Slice<FileStorageEntity>> result = fileStorageController.getSortedFeedPage(1, "ASC");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(slice, result.getBody());
        verify(fileStorageService, times(1)).getFeedPage(0, SortType.ASC);
    }

    @Test
    void getSortedFeedPage_invalidSort_returnsUnsortedSlice() {
        Slice<FileStorageEntity> slice = new SliceImpl<>(Collections.singletonList(entity));
        when(fileStorageService.getFeedPage(0, SortType.UNSORTED)).thenReturn(slice);

        ResponseEntity<Slice<FileStorageEntity>> result = fileStorageController.getSortedFeedPage(1, "TEST");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(slice, result.getBody());
        verify(fileStorageService, times(1)).getFeedPage(0, SortType.UNSORTED);
    }

    @Test
    void handleException_Exception_ResponseWithExceptionMessage() {
        Exception ex = new RuntimeException("Test exception");

        ResponseEntity<ExceptionResponse> result = fileStorageController.handleException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(new ExceptionResponse(ex.getMessage()), result.getBody());
    }

}
