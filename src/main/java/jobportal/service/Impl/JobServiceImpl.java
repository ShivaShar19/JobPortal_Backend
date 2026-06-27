package jobportal.service.Impl;

import jobportal.dto.request.JobRequest;
import jobportal.dto.response.JobResponse;
import jobportal.entity.Job;
import jobportal.entity.User;
import jobportal.exception.JobNotFoundException;
import jobportal.exception.JobOwnershipException;
import jobportal.exception.UserNotFoundException;
import jobportal.repository.JobRepository;
import jobportal.repository.UserRepository;
import jobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override
    public JobResponse createJob(
            JobRequest request,
            String recruiterEmail) {

        User recruiter = userRepository
                .findByEmail(recruiterEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Recruiter not found"));

        Job job = Job.builder()
                .title(request.getTitle())
                .companyName(request.getCompanyName())
                .location(request.getLocation())
                .description(request.getDescription())
                .salary(request.getSalary())
                .jobType(request.getJobType())
                .createdAt(LocalDateTime.now())
                .recruiter(recruiter)
                .build();

        Job savedJob = jobRepository.save(job);

        return JobResponse.builder()
                .id(savedJob.getId())
                .title(savedJob.getTitle())
                .companyName(savedJob.getCompanyName())
                .location(savedJob.getLocation())
                .description(savedJob.getDescription())
                .salary(savedJob.getSalary())
                .jobType(savedJob.getJobType())
                .createdAt(savedJob.getCreatedAt())
                .build();
    }

    @Override
    public List<JobResponse> getAllJobs() {

        return jobRepository.findAll()
                .stream()
                .map(job -> JobResponse.builder()
                        .id(job.getId())
                        .title(job.getTitle())
                        .companyName(job.getCompanyName())
                        .location(job.getLocation())
                        .description(job.getDescription())
                        .salary(job.getSalary())
                        .jobType(job.getJobType())
                        .createdAt(job.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public JobResponse getJobById(Long id) {

        Job job = jobRepository
                .findById(id)
                .orElseThrow(() ->
                        new JobNotFoundException(
                                "Job not found"));

        return JobResponse.builder()
                        .id(job.getId())
                        .title(job.getTitle())
                        .companyName(job.getCompanyName())
                        .location(job.getLocation())
                        .description(job.getDescription())
                        .salary(job.getSalary())
                        .jobType(job.getJobType())
                        .createdAt(job.getCreatedAt())
                        .build();
    }

    @Override
    public JobResponse updateJob(Long jobId, JobRequest request, String recruiterEmail) {
        User recruiter = userRepository
                .findByEmail(recruiterEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Recruiter not found"));

        Job job = jobRepository
                .findById(jobId)
                .orElseThrow(() ->
                        new JobNotFoundException(
                                "Job not found"));

        if (!job.getRecruiter()
                .getId()
                .equals(recruiter.getId())) {

            throw new JobOwnershipException(
                    "You can only update your own jobs");
        }

        job.setTitle(request.getTitle());
        job.setCompanyName(request.getCompanyName());
        job.setLocation(request.getLocation());
        job.setDescription(request.getDescription());
        job.setSalary(request.getSalary());
        job.setJobType(request.getJobType());

        Job updatedJob = jobRepository.save(job);

        return JobResponse.builder()
                .id(updatedJob.getId())
                .title(updatedJob.getTitle())
                .companyName(updatedJob.getCompanyName())
                .location(updatedJob.getLocation())
                .description(updatedJob.getDescription())
                .salary(updatedJob.getSalary())
                .jobType(updatedJob.getJobType())
                .createdAt(updatedJob.getCreatedAt())
                .build();
    }

    @Override
    public void deleteJob(Long jobId, String recruiterEmail) {

        User recruiter = userRepository
                .findByEmail(recruiterEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Recruiter not found"));

        Job job = jobRepository
                .findById(jobId)
                .orElseThrow(() ->
                        new JobNotFoundException(
                                "Job not found"));

        if (!job.getRecruiter()
                .getId()
                .equals(recruiter.getId())) {

            throw new JobOwnershipException(
                    "You can only delete your own jobs");
        }

        jobRepository.delete(job);
    }


}
