package jobportal.service;

import jobportal.dto.request.JobRequest;
import jobportal.dto.response.JobResponse;

public interface JobService {

    JobResponse createJob(JobRequest request, String recruiterEmail);

}
