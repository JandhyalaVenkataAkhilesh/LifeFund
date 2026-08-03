package com.akhilesh.LifeFund.controller;

import com.akhilesh.LifeFund.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/{folderName}/{fileName}")
    public ResponseEntity<Resource> getFile(
            @PathVariable String folderName,
            @PathVariable String fileName) {

        Resource resource = fileService.getFile(folderName, fileName);

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        String lowerCaseFileName = fileName.toLowerCase();

        if (lowerCaseFileName.endsWith(".pdf")) {

            mediaType = MediaType.APPLICATION_PDF;

        } else if (lowerCaseFileName.endsWith(".jpg")
                || lowerCaseFileName.endsWith(".jpeg")) {

            mediaType = MediaType.IMAGE_JPEG;

        } else if (lowerCaseFileName.endsWith(".png")) {

            mediaType = MediaType.IMAGE_PNG;

        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + fileName + "\""
                )
                .body(resource);

    }

}