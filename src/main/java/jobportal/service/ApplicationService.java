package jobportal.service;

import jobportal.dto.request.ApplyJobRequest;
import jobportal.dto.response.ApplicationResponse;
import jobportal.entity.enums.ApplicationStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApplicationService {

    ApplicationResponse applyForJob(Long jobId, MultipartFile resume, String jobSeekerEmail);

    List<ApplicationResponse> getMyApplications(String jobSeekerEmail);

    List<ApplicationResponse> getApplicantsForJob(Long jobId, String recruiterEmail);

    ApplicationResponse updateApplicationStatus(Long applicationId, ApplicationStatus status, String recruiterEmail);

    List<ApplicationResponse> getAllApplicationsForRecruiter(String recruiterEmail);

    void withdrawApplication(Long applicationId);

}
