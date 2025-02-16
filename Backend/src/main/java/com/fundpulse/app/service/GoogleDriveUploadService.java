package com.fundpulse.app.service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class GoogleDriveUploadService {

    @Autowired
    private DocumentUploadService documentUploadService; // Your Drive config

    public String uploadFile(MultipartFile file) throws IOException {
        // Get Drive instance
        Drive drive = documentUploadService.getDriveInstance();

        // Convert MultipartFile to a temporary file
        java.io.File tempFile = java.io.File.createTempFile("upload-", ".pdf");
        Files.copy(file.getInputStream(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // Set file metadata
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setMimeType("application/pdf");

        // Upload the file to Google Drive
        File uploadedFile = drive.files().create(fileMetadata, new com.google.api.client.http.FileContent("application/pdf", tempFile))
                .setFields("id, webViewLink")
                .execute();

        // Make the file publicly accessible
        Permission permission = new Permission()
                .setType("anyone")   // Public access
                .setRole("reader");  // Read-only permission
        drive.permissions().create(uploadedFile.getId(), permission).execute();

        // Return the sharable link
        return uploadedFile.getWebViewLink();
    }
}
