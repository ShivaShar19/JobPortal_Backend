package jobportal.service;

import jobportal.dto.request.JobRequest;
import jobportal.dto.response.JobResponse;

import java.util.List;

public interface JobService {

    List<JobResponse> getRecruiterJobs(String recruiterEmail);

    JobResponse createJob(JobRequest request, String recruiterEmail);

    List<JobResponse> getAllJobs();

    JobResponse getJobById(Long id);

    JobResponse updateJob(Long jobId, JobRequest request, String recruiterEmail);

    void deleteJob(Long jobId, String recruiterEmail);

}
