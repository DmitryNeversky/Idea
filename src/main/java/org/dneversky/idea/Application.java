package org.dneversky.idea;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class Application {

    @Value("${uploadPath}")
    private static String uploadPath;

    public static void main(String[] args) throws IOException {
        if(!Files.exists(Paths.get(uploadPath))) {
            Files.createDirectory(Paths.get(uploadPath));
            Files.createDirectory(Paths.get(uploadPath + "/images"));
            Files.createDirectory(Paths.get(uploadPath + "/images/avatar"));
            Files.createDirectory(Paths.get(uploadPath + "/files"));
        }
        SpringApplication.run(Application.class, args);
    }
}
