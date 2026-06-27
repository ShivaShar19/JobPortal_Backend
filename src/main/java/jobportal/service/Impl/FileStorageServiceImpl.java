package jobportal.service.Impl;

import jobportal.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final String uploadDir = "uploads/resumes/";

    @Override
    public String uploadResume(MultipartFile file) {
        try {

            String fileName =
                    System.currentTimeMillis()
                            + "_"
                            + file.getOriginalFilename();

            Path path = Paths.get(
                    uploadDir,
                    fileName);

            Files.createDirectories(
                    path.getParent());

            Files.copy(
                    file.getInputStream(),
                    path,
                    StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to upload resume");
        }
    }
}
