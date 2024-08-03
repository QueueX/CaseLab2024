package org.greenatom.filestorageservice.service;

import org.greenatom.filestorageservice.dto.FileId;
import org.greenatom.filestorageservice.dto.FileInfo;
import org.greenatom.filestorageservice.entity.FileStorageEntity;
import org.greenatom.filestorageservice.repository.FileStorageRepository;
import org.greenatom.filestorageservice.util.FileNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}
