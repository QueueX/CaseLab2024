package org.greenatom.filestorageservice.repository;

import org.greenatom.filestorageservice.entity.FileStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorageEntity, Long> {
    Optional<FileStorageEntity> findById(Long id);
}
