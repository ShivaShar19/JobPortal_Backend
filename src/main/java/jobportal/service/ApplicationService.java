package jobportal.service;

import jobportal.dto.request.ApplyJobRequest;
import jobportal.dto.response.ApplicationResponse;

import java.util.List;

public interface ApplicationService {

    ApplicationResponse applyForJob(Long jobId, ApplyJobRequest request, String jobSeekerEmail);

    List<ApplicationResponse> getMyApplications(String jobSeekerEmail);

}
