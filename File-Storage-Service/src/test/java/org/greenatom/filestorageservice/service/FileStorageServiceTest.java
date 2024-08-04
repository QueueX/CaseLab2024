package org.greenatom.filestorageservice.service;

import org.greenatom.filestorageservice.dto.FileId;
import org.greenatom.filestorageservice.dto.FileInfo;
import org.greenatom.filestorageservice.entity.FileStorageEntity;
import org.greenatom.filestorageservice.repository.FileStorageRepository;
import org.greenatom.filestorageservice.util.FileNotFoundException;
import org.greenatom.filestorageservice.util.SortType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FileStorageServiceTest {

    @Mock
    private FileStorageRepository fileStorageRepository;

    @InjectMocks
    private FileStorageService fileStorageService;

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

    @Test
    void createFile_validData_returnedValidFileId() {
        entity.setId(id);

        when(fileStorageRepository.save(any(FileStorageEntity.class))).thenReturn(entity);

        FileId response = fileStorageService.createFile(fileInfo);

        assertEquals(new FileId(id), response);
        verify(fileStorageRepository, times(1)).save(any(FileStorageEntity.class));
    }

    @Test
    void getFile_fileExists_returnFileInfo() {
        when(fileStorageRepository.findById(id)).thenReturn(Optional.of(entity));

        FileInfo result = fileStorageService.getFile(id);

        assertEquals(fileInfo, result);
        verify(fileStorageRepository, times(1)).findById(id);
    }

    @Test
    void getFile_fileDoesNotExist_throwFileNotFoundException() {
        when(fileStorageRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () -> fileStorageService.getFile(id));
        verify(fileStorageRepository, times(1)).findById(id);
    }

    @Test
    void getFeedPage_noSort_returnsUnsortedSlice() {
        Page<FileStorageEntity> page = new PageImpl<>(Collections.singletonList(entity));
        Pageable pageable = PageRequest.of(0, 10);
        when(fileStorageRepository.findAll(pageable)).thenReturn(page);

        Slice<FileStorageEntity> result = fileStorageService.getFeedPage(0, SortType.UNSORTED);

        assertEquals(page, result);
        verify(fileStorageRepository, times(1)).findAll(pageable);
    }

    @Test
    void getFeedPage_withSortAsc_returnsSortedSlice() {
        Page<FileStorageEntity> page = new PageImpl<>(Collections.singletonList(entity));
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("creationDate")));
        when(fileStorageRepository.findAll(pageable)).thenReturn(page);

        Slice<FileStorageEntity> result = fileStorageService.getFeedPage(0, SortType.ASC);

        assertEquals(page, result);
        verify(fileStorageRepository, times(1)).findAll(pageable);
    }

    @Test
    void getFeedPage_withSortDesc_returnsSortedSlice() {
        Page<FileStorageEntity> page = new PageImpl<>(Collections.singletonList(entity));
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("creationDate")));
        when(fileStorageRepository.findAll(pageable)).thenReturn(page);

        Slice<FileStorageEntity> result = fileStorageService.getFeedPage(0, SortType.DESC);

        assertEquals(page, result);
        verify(fileStorageRepository, times(1)).findAll(pageable);
    }

}
