package jobportal.controller;

import jakarta.validation.Valid;
import jobportal.dto.request.JobRequest;
import jobportal.dto.request.UpdateApplicationStatusRequest;
import jobportal.dto.response.ApplicationResponse;
import jobportal.dto.response.JobResponse;
import jobportal.service.ApplicationService;
import jobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiter/jobs")
@RequiredArgsConstructor
public class RecruiterJobController {

    private final JobService jobService;
    private final ApplicationService applicationService;

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

    @GetMapping("/{jobId}/applications")
    public ResponseEntity<List<ApplicationResponse>>
    getApplicantsForJob(
            @PathVariable Long jobId,
            Authentication authentication
    ) {

        System.out.println(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getAuthorities()
        );

        return ResponseEntity.ok(
                applicationService.getApplicantsForJob(
                        jobId,
                        authentication.getName()
                )
        );
    }

    @PutMapping("/applications/{applicationId}/status")
    public ResponseEntity<ApplicationResponse>
    updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestBody UpdateApplicationStatusRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                applicationService.updateApplicationStatus(
                        applicationId,
                        request.getStatus(),
                        authentication.getName()
                )
        );
    }

    @GetMapping("/applications")
    public ResponseEntity<List<ApplicationResponse>>
    getAllApplications(
            Authentication authentication) {

        return ResponseEntity.ok(
                applicationService.getAllApplicationsForRecruiter(
                        authentication.getName()
                )
        );
    }

}
