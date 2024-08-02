package org.greenatom.filestorageservice.service;

import jakarta.transaction.Transactional;
import org.greenatom.filestorageservice.entity.FileStorageEntity;
import org.greenatom.filestorageservice.repository.FileStorageRepository;
import org.greenatom.filestorageservice.request.CreateFileRequest;
import org.greenatom.filestorageservice.response.CreateFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FileStorageService {
    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    private final FileStorageRepository fileStorageRepository;

    public FileStorageService(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
    }

    @Transactional
    public CreateFileResponse createFile(CreateFileRequest request) {
        FileStorageEntity entity = new FileStorageEntity(
                request.getTitle(),
                request.getCreationDate(),
                request.getDescription(),
                request.getFile()
        );
        logger.info("File has been created");
        logger.debug("File: {}", entity);

        fileStorageRepository.save(entity);

        return new CreateFileResponse(entity.getId());
    }

}
