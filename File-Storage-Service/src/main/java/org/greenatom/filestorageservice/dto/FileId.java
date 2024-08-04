package org.greenatom.filestorageservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "id пользователя")
public class FileId {
    @Schema(example = "1")
    private final Long id;
}
