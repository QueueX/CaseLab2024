package org.greenatom.filestorageservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.greenatom.filestorageservice.entity.FileStorageEntity;

import java.util.Date;

@Data
@Schema(description = "Файл и его аттрибуты")
public class FileInfo {
    @Schema(description = "Название файла", example = "Title")
    private final String title;
    private final Date creationDate;
    @Schema(description = "Описание файла", example = "This is description of file")
    private final String description;
    @Schema(description = "Файл в формате base64", example = "/9j/4AAQSkZJRgABAQAAAQABAAD/2114=")
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
