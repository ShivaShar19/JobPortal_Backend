package jobportal.controller;

import jakarta.validation.Valid;
import jobportal.dto.request.JobRequest;
import jobportal.dto.response.JobResponse;
import jobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiter/jobs")
@RequiredArgsConstructor
public class RecruiterJobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobResponse> createJob(
            @Valid @RequestBody JobRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                jobService.createJob(
                        request,
                        email
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                jobService.updateJob(
                        id,
                        request,
                        authentication.getName()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(
            @PathVariable Long id,
            Authentication authentication) {

        jobService.deleteJob(
                id,
                authentication.getName());

        return ResponseEntity.ok(
                "Job deleted successfully");
    }
}
