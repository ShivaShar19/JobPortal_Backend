package jobportal.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final Path uploadPath =
            Paths.get("uploads/resumes");

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getResume(
            @PathVariable String fileName) {

        try {

            Path file =
                    uploadPath.resolve(fileName);

            Resource resource =
                    new UrlResource(file.toUri());

            if (!resource.exists()) {
                throw new RuntimeException(
                        "Resume not found");
            }

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" +
                                    resource.getFilename() +
                                    "\"")
                    .body(resource);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to load resume");
        }
    }

}
