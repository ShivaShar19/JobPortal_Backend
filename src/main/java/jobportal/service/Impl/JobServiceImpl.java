package jobportal.service.Impl;

import jobportal.dto.request.JobRequest;
import jobportal.dto.response.JobResponse;
import jobportal.entity.Job;
import jobportal.entity.User;
import jobportal.exception.UserNotFoundException;
import jobportal.repository.JobRepository;
import jobportal.repository.UserRepository;
import jobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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


}
