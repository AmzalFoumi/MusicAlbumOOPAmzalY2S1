package com.amzal.util;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class responsible for handling audio file uploads within the web application.
 * It provides a static method to save the uploaded file to a designated directory
 * and returns the relative path for accessing the file.
 */
public class AudioUploadService {

    /**
     * Handles the file upload part, saving the audio file to the server and
     * returning the relative file path. This method is static.
     *
     * @param filePart        The Part representing the uploaded audio file.
     * @param servletContext  The ServletContext to get the real path for uploads.
     * @param originalFileName The original name of the uploaded file.
     * @return The relative path to the saved audio file within the web application.
     * @throws IOException If an I/O error occurs during file processing.
     */
    public static String handleAudioUpload(Part filePart, ServletContext servletContext, String originalFileName) throws IOException {
        //Getting the time and formatting it
    	LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedTimestamp = now.format(formatter);

        // Creating a unique file name with timestamp
        String uniqueFileName = formattedTimestamp + "_Amzal_" + originalFileName;

        // Get the absolute path to /webapp/audiouploads
        String uploadsDir = servletContext.getRealPath("/audiouploads");
        File uploadFolder = new File(uploadsDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        // Create file reference and save
        File savedFile = new File(uploadFolder, uniqueFileName);
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        // Save relative file path (to use in JSPs)
        return "audiouploads/" + uniqueFileName;
    }
}