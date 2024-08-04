package org.greenatom.filestorageservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.greenatom.filestorageservice.entity.FileStorageEntity;
import org.greenatom.filestorageservice.repository.FileStorageRepository;
import org.greenatom.filestorageservice.dto.FileInfo;
import org.greenatom.filestorageservice.dto.FileId;
import org.greenatom.filestorageservice.util.FileNotFoundException;
import org.greenatom.filestorageservice.util.SortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileStorageService {
    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private final FileStorageRepository fileStorageRepository;

    @Transactional
    public FileId createFile(FileInfo request) {
        FileStorageEntity entity = new FileStorageEntity(
                request.getTitle(),
                request.getCreationDate(),
                request.getDescription(),
                request.getFile()
        );
        logger.info("File has been created");
        logger.debug("File: {}", entity);

        entity = fileStorageRepository.save(entity);

        return new FileId(entity.getId());
    }

    public FileInfo getFile(Long id) {
        Optional<FileStorageEntity> optionalFileStorage = fileStorageRepository.findById(id);

        if (optionalFileStorage.isEmpty()) throw new FileNotFoundException();

        FileStorageEntity entity = optionalFileStorage.get();
        logger.debug("Entity: {}", entity);

        return FileInfo.of(entity);
    }

    public Page<FileStorageEntity> getFeedPage(int page, SortType sortType) {
        Pageable pageable;

        switch (sortType) {
            case ASC -> pageable = PageRequest.of(page,10, Sort.by(Sort.Order.asc("creationDate")));
            case DESC -> pageable = PageRequest.of(page,10, Sort.by(Sort.Order.desc("creationDate")));
            default -> pageable = PageRequest.of(page, 10);
        }
        logger.debug("Sort type is {}", sortType);

        return fileStorageRepository.findAll(pageable);
    }
}
