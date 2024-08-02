package org.greenatom.filestorageservice.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CreateFileRequest {
    private final String title;
    private final Date creationDate;
    private final String description;
    private final byte[] file;
}
