package jobportal.service.Impl;

import jobportal.dto.response.ApplicationResponse;
import jobportal.dto.response.RecruiterDashboardResponse;
import jobportal.entity.User;
import jobportal.entity.enums.ApplicationStatus;
import jobportal.exception.UserNotFoundException;
import jobportal.repository.ApplicationRepository;
import jobportal.repository.JobRepository;
import jobportal.repository.UserRepository;
import jobportal.service.RecruiterDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecruiterDashboardServiceImpl
        implements RecruiterDashboardService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public RecruiterDashboardResponse getDashboard(
            String recruiterEmail) {

        User recruiter = userRepository
                .findByEmail(recruiterEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"));

        long totalJobs =
                jobRepository.countByRecruiterId(
                        recruiter.getId());

        long totalApplications =
                applicationRepository
                        .countByJobRecruiterId(
                                recruiter.getId());

        Map<ApplicationStatus, Long> statusCounts =
                new EnumMap<>(ApplicationStatus.class);

        for (ApplicationStatus status :
                ApplicationStatus.values()) {

            long count = applicationRepository
                    .findByJobRecruiterId(
                            recruiter.getId())
                    .stream()
                    .filter(application ->
                            application.getStatus() == status)
                    .count();

            statusCounts.put(status, count);
        }

        return RecruiterDashboardResponse.builder()
                .totalJobs(totalJobs)
                .totalApplications(totalApplications)
                .applicationsByStatus(statusCounts)
                .recentApplications(
                        applicationRepository
                                .findTop5ByJobRecruiterIdOrderByAppliedAtDesc(
                                        recruiter.getId())
                                .stream()
                                .map(application ->
                                             ApplicationResponse.builder()
                                                .id(application.getId())
                                                .jobId(application.getJob().getId())
                                                .jobTitle(application.getJob().getTitle())
                                                .applicantName(application.getJobSeeker().getName())
                                                .applicantEmail(application.getJobSeeker().getEmail())
                                                .resumeUrl("/api/resumes/" + application.getResumeUrl())
                                                .status(application.getStatus())
                                                .appliedAt(application.getAppliedAt())
                                                .build())
                                .toList()
                )
                .build();
    }
}