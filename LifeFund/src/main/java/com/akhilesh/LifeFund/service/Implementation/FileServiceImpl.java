package com.akhilesh.LifeFund.service.Implementation;

import com.akhilesh.LifeFund.exceptions.FileStorageException;
import com.akhilesh.LifeFund.service.FileService;
import com.akhilesh.LifeFund.utils.FileConstants;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public Resource getFile(String folderName, String fileName) {

        try {

            Path filePath = Paths.get(
                    FileConstants.UPLOAD_DIRECTORY,
                    folderName,
                    fileName
            );

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }

            throw new FileStorageException("File not found.");

        } catch (MalformedURLException e) {
            throw new FileStorageException("Unable to read file.");
        }
    }
}