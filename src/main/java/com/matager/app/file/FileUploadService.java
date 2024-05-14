package com.matager.app.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String upload(String prefix, MultipartFile file);
    String upload(MultipartFile file) throws Exception;

    String upload(FileType fileType, MultipartFile file);
}
