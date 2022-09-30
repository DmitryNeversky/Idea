package org.dneversky.idea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        Application.createDirectories();
        SpringApplication.run(Application.class, args);
    }

    private static void createDirectories() throws IOException {
//        String UPLOAD_PATH = System.getenv("UPLOAD_PATH");  // for production
        String UPLOAD_PATH = "/home/koshey/uploads/";
        if(!Files.exists(Paths.get(UPLOAD_PATH))) {
            Files.createDirectory(Paths.get(UPLOAD_PATH));
            Files.createDirectory(Paths.get(UPLOAD_PATH + "/files"));
            Files.createDirectories(Paths.get(UPLOAD_PATH + "/images/avatar"));
        }
    }
}
