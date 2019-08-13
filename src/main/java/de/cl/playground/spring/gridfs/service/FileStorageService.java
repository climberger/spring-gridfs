package de.cl.playground.spring.gridfs.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import de.cl.playground.spring.gridfs.model.FileData;
import de.cl.playground.spring.gridfs.persistence.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileStorageService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private FileDataRepository fileDataRepo;

    @Autowired
    private MongoDbFactory mongoDbFactory;

    public FileData storeFile(InputStream in, String fileName) {

        String fileId = UUID.randomUUID().toString();

        FileData fileData = new FileData();
        fileData.setId(fileId);
        fileData.setFileName(fileName);
        fileDataRepo.save(fileData);

        DBObject metaData = new BasicDBObject();
        metaData.put("fileId", fileId);
        gridFsTemplate.store(in, fileName, metaData);

        return fileData;
    }

    public void deleteFile(String fileId) {
        gridFsTemplate.delete(new Query(Criteria.where("metadata.fileId").is(fileId)));
    }

    public InputStream getFileContent(String fileId) {

        GridFSFile gridFsFile = gridFsTemplate.findOne(new Query(Criteria.where("metadata.fileId").is(fileId)));
        GridFsResource gridFsRes = new GridFsResource(gridFsFile, getGridFs().openDownloadStream(gridFsFile.getObjectId()));

        InputStream input = null;
        try {
            input = gridFsRes.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    private GridFSBucket getGridFs() {

        MongoDatabase db = mongoDbFactory.getDb();
        return GridFSBuckets.create(db);
    }

}
