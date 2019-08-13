package de.cl.playground.spring.gridfs.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "file-data")
public class FileData {

    @Id
    public String id;

    public String fileName;
}
