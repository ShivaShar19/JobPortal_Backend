package jobportal.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String uploadResume(MultipartFile file);
}
