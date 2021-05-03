package com.smart.contact.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    boolean uploadFile(MultipartFile multipartFile, String fileName);

}
