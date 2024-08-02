package org.greenatom.filestorageservice.util;

public class FileNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "File not found";
    }
}
