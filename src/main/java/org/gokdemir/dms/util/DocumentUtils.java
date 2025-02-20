package org.gokdemir.dms.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class DocumentUtils {

    public static String generateUniqueFilename(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return UUID.randomUUID() + "_" + timestamp + extension;
    }

    public static void saveFile(MultipartFile file, Path filePath) throws IOException {
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    public static void moveFile(Path oldPath, Path newPath) throws IOException {
        Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
    }

    public static byte[] readFile(Path filePath) throws IOException {
        return Files.readAllBytes(filePath);
    }
}