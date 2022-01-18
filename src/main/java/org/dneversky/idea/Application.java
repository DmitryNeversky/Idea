package org.dneversky.idea;

import org.dneversky.idea.util.PropertiesLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IOException {
        String UPLOAD_PATH = PropertiesLoader
                .loadProperties("application.yaml")
                .getProperty("uploadPath");
        if(!Files.exists(Paths.get(UPLOAD_PATH))) {
            Files.createDirectory(Paths.get(UPLOAD_PATH));
            Files.createDirectory(Paths.get(UPLOAD_PATH + "/images"));
            Files.createDirectory(Paths.get(UPLOAD_PATH + "/images/avatar"));
            Files.createDirectory(Paths.get(UPLOAD_PATH + "/files"));
        }
        SpringApplication.run(Application.class, args);
    }
}
