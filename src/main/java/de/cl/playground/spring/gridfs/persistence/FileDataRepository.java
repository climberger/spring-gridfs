package de.cl.playground.spring.gridfs.persistence;

import de.cl.playground.spring.gridfs.model.FileData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileDataRepository extends MongoRepository<FileData, String> {

    public FileData findByFileName(String fileName);

}
