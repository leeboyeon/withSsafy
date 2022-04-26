package com.ssafy.withssafy.util;

import com.ssafy.withssafy.errorcode.ErrorCode;
import com.ssafy.withssafy.exception.InternalServerException;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

public class FileManager {

    private static final Path serverLocation = Paths.get("/home/ubuntu/images/").normalize();

    public static String save(MultipartFile multipartFile, Long userId) {
        String filename = generateFilename(userId);
        Path location = serverLocation.resolve(filename);
        try {
            Files.copy(multipartFile.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new InternalServerException(ErrorCode.FAILED_TO_SAVE_FILE);
        }

        return filename;
    }

    public static String getFile(String filePath) {
        if (filePath == null)
            return null;

        Path file = serverLocation.resolve(filePath).normalize();
        FileSystemResource resource = new FileSystemResource(file);

        if (resource.exists() || resource.isReadable()) {
            try {
                byte[] fileContent = FileUtils.readFileToByteArray(new File(file.toUri()));
                return Base64.getEncoder().encodeToString(fileContent);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
        return null;
    }

    private static String generateFilename(Long userId) {
        return String.format("%d_%d", userId, System.currentTimeMillis());
    }
}
