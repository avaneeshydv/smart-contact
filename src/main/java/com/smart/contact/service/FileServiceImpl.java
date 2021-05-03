package com.smart.contact.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    public boolean uploadFile(MultipartFile multipartFile, String fileName) {
        try {

            StringBuilder path = new StringBuilder();
            path.append(new ClassPathResource("static/image").getFile().getAbsolutePath()).append(File.separator)
                    .append(LocalDateTime.now()).append(fileName);

            Files.copy(multipartFile.getInputStream(), Paths.get(path.toString()), StandardCopyOption.REPLACE_EXISTING);
            return true;

        } catch (IOException e) {
            log.error("Error occurred while uploading image  for contact id {}", fileName);
        }
        return false;
    }

}
