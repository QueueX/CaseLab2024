package org.greenatom.filestorageservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "file_storage")
@NoArgsConstructor
@Getter
@Setter
public class FileStorageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "creation_date", unique = false, nullable = false)
    private Date creationDate;

    @Column(name = "description", unique = false, nullable = false)
    private String description;

    @Column(name = "file", unique = false, nullable = false)
    private byte[] file;

    public FileStorageEntity(String title, Date creationDate, String description, byte[] file) {
        this.title = title;
        this.creationDate = creationDate;
        this.description = description;
        this.file = file;
    }
}
