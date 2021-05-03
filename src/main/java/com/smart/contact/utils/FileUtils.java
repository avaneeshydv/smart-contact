package com.smart.contact.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    public static String validateFile(MultipartFile file) {

        if (!(StringUtils.equalsIgnoreCase(file.getContentType(), "image/jpeg")
                || StringUtils.equalsIgnoreCase(file.getContentType(), "image/jpg")
                || StringUtils.equalsIgnoreCase(file.getContentType(), "image/png"))) {
            return "Unsupported file type!!";
        } else if (file.isEmpty()) {
            return "Empty file!!";
        } else {
            return "";
        }
    }

}