package com.matager.app.common.config;

import org.springframework.context.annotation.Configuration;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.util.ResourceUtils;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;

@Configuration
public class ObjectStorageClientConfig {
    // Path to OCI config file
    String configurationFilePath = "classpath:config/OCI/config";
    String profile = "DEFAULT";


    public ObjectStorage getObjectStorage() throws IOException {

        final ConfigFileReader.ConfigFile
                configFile = ConfigFileReader
                .parse(new FileInputStream(ResourceUtils.getFile(configurationFilePath)), profile);



        final ConfigFileAuthenticationDetailsProvider provider =
                new ConfigFileAuthenticationDetailsProvider(configFile);

        return ObjectStorageClient.builder()
                .build(provider);
    }

}
