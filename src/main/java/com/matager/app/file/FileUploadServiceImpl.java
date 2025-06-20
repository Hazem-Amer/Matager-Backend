package com.matager.app.file;

import com.matager.app.common.config.ObjectStorageClientConfig;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileUploadServiceImpl implements FileUploadService {

    String accessUrl = "https://axy4miqdo4au.objectstorage.me-jeddah-1.oci.customer-oci.com/p/g97IbWpkFCVFdGCg_N5OvVNqeIcmwkVv4hrUJ0T5xQySn1JJt5VQYvj4TLRi3h-I/n/axy4miqdo4au/b/matager-object-storage/o/";
    String bucketName = "matager-object-storage";
    String namespaceName = "axy4miqdo4au";
    private final ObjectStorageClientConfig objectStorageClientConfig;

    @Override
    public String upload(String prefix, MultipartFile file) {
        String objectName = prefix + file.getOriginalFilename();

        try {
            InputStream inputStream = file.getInputStream();

            PutObjectRequest putObjectRequest =
                    PutObjectRequest.builder()
                            .namespaceName(namespaceName)
                            .bucketName(bucketName)
                            .objectName(objectName)
                            .contentType(file.getContentType())
                            .contentLength(file.getSize())
                            .contentType(file.getContentType())
                            .putObjectBody(inputStream)
                            .build();

            objectStorageClientConfig.getObjectStorage().putObject(putObjectRequest);
        } catch (Exception e) {
            // TODO: add logging here
            log.error("Error uploading file..", e);
        } finally {
            try {
                objectStorageClientConfig.getObjectStorage().close();
            } catch (Exception e) {
                // TODO: add logging here
            }
        }

        return accessUrl + objectName;
    }

    @Override
    public String upload(MultipartFile file) throws Exception {
        return upload("", file);
    }

    @Override
    public String upload(FileType fileType, MultipartFile file) {
        return upload(fileType.getPrefix(), file);
    }
}
