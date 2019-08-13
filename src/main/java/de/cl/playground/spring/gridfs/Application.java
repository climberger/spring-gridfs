package de.cl.playground.spring.gridfs;

import de.cl.playground.spring.gridfs.model.FileData;
import de.cl.playground.spring.gridfs.service.FileStorageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@SpringBootApplication
public class Application implements CommandLineRunner {



	@Autowired
	private FileStorageService fileStorageService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws IOException {

		System.out.println("Command Line Runner started.");

		// Store file
		String fileName = "sample.txt";
		InputStream input = new FileInputStream(fileName);
		FileData fileData = fileStorageService.storeFile(input, fileName);

		// Retrieve file
		InputStream retrivedIn = fileStorageService.getFileContent(fileData.id);


		String fileContent = IOUtils.toString(retrivedIn, Charset.defaultCharset());
		System.out.println(fileContent);

	}

}
