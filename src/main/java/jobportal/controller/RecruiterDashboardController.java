package jobportal.controller;

import jobportal.dto.response.RecruiterDashboardResponse;
import jobportal.service.RecruiterDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recruiter/dashboard")
@RequiredArgsConstructor
public class RecruiterDashboardController {

    private final RecruiterDashboardService recruiterDashboardService;

    @GetMapping
    public ResponseEntity<RecruiterDashboardResponse>
    getDashboard(Authentication authentication) {

        return ResponseEntity.ok(
                recruiterDashboardService.getDashboard(
                        authentication.getName()
                )
        );
    }
}