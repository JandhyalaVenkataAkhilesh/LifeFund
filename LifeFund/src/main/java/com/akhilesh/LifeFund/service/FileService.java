package com.akhilesh.LifeFund.service;

import org.springframework.core.io.Resource;

public interface FileService {
    Resource getFile(String folderName, String fileName);
}