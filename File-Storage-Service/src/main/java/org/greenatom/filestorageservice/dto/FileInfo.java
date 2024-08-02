package org.greenatom.filestorageservice.dto;

import lombok.Data;
import org.greenatom.filestorageservice.entity.FileStorageEntity;

import java.util.Date;

@Data
public class FileInfo {
    private final String title;
    private final Date creationDate;
    private final String description;
    private final byte[] file;

    public static FileInfo of(FileStorageEntity entity) {
        return new FileInfo(
                entity.getTitle(),
                entity.getCreationDate(),
                entity.getDescription(),
                entity.getFile()
        );
    }
}
