package com.matager.app;

import com.matager.app.file.FileUploadService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.mock.web.MockMultipartFile;


@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Bean
    public CommandLineRunner playGround(FileUploadService fileUploadService){
        return (args)->{
//            System.out.println("-----------------");
//            Path path = Paths.get("test.txt");
//            String name = "test.txt";
//            String originalFileName = "test.txt";
//            String contentType = "text/plain";
//            byte[] content = null;
//            try {
//                content = Files.readAllBytes(path);
//            } catch (final IOException e) {
//            }
//            MultipartFile result = new MockMultipartFile(name,
//                    originalFileName, contentType, content);
//
//            System.out.println(fileUploadService.upload("", result));

        };
    }

}
