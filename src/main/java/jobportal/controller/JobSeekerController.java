package jobportal.controller;

import jakarta.validation.Valid;
import jobportal.dto.request.ApplyJobRequest;
import jobportal.dto.response.ApplicationResponse;
import jobportal.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/jobseeker")
@RequiredArgsConstructor
public class JobSeekerController {

    private final ApplicationService applicationService;

    @PostMapping(value = "/jobs/{jobId}/apply", consumes = "multipart/form-data")
    public ResponseEntity<ApplicationResponse> applyForJob(@PathVariable Long jobId,
                                                           @Valid
                                                           @RequestParam("resume")
                                                           MultipartFile resume,
                                                           Authentication authentication)
    {
        return ResponseEntity.ok(applicationService.applyForJob(jobId,resume,authentication.getName()));
    }

    @GetMapping("/applications")
    public ResponseEntity<List<ApplicationResponse>>
    getMyApplications(
            Authentication authentication) {

        return ResponseEntity.ok(
                applicationService.getMyApplications(
                        authentication.getName()
                )
        );
    }

}
