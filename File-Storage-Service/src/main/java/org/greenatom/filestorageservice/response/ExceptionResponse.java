package org.greenatom.filestorageservice.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Ответ с ошибкой")
public class ExceptionResponse {
    @Schema(description = "Описание ошибки", example = "Something wrong. PLS BUGFIX!!!")
    private final String error;
}
