package jobportal.service.Impl;

import jobportal.dto.request.ApplyJobRequest;
import jobportal.dto.response.ApplicationResponse;
import jobportal.entity.Application;
import jobportal.entity.Job;
import jobportal.entity.User;
import jobportal.entity.enums.ApplicationStatus;
import jobportal.exception.DuplicateApplicationException;
import jobportal.exception.JobNotFoundException;
import jobportal.exception.JobOwnershipException;
import jobportal.exception.UserNotFoundException;
import jobportal.repository.ApplicationRepository;
import jobportal.repository.JobRepository;
import jobportal.repository.UserRepository;
import jobportal.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
//    private final ApplicationResponse application;


    @Override
    public ApplicationResponse applyForJob(Long jobId, ApplyJobRequest request, String jobSeekerEmail) {

        Job job = jobRepository
                .findById(jobId)
                .orElseThrow(() ->
                        new JobNotFoundException(
                                "Job not found"));

        User user = userRepository
                .findByEmail(jobSeekerEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "jobSeeker not found"));

        if (applicationRepository.existsByJobIdAndJobSeekerId(
                job.getId(),
                user.getId())) {

            throw new DuplicateApplicationException(
                    "You have already applied for this job");
        }

        Application application = Application.builder()
                .resumeUrl(request.getResumeUrl())
                .job(job)
                .jobSeeker(user)
                .status(ApplicationStatus.APPLIED)
                .appliedAt(LocalDateTime.now())
                .build();

        Application applicationSaved = applicationRepository.save(application);

        return ApplicationResponse.builder()
                .id(applicationSaved.getId())
                .jobId(applicationSaved.getJob().getId())
                .jobTitle(applicationSaved.getJob().getTitle())
                .resumeUrl(applicationSaved.getResumeUrl())
                .applicantName(applicationSaved.getJobSeeker().getName())
                .applicantEmail(applicationSaved.getJobSeeker().getEmail())
                .status(applicationSaved.getStatus())
                .appliedAt(applicationSaved.getAppliedAt())
                .build();
    }

    @Override
    public List<ApplicationResponse> getMyApplications(String jobSeekerEmail) {

        User user = userRepository
                .findByEmail(jobSeekerEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "jobSeeker not found"));

        return applicationRepository.findByJobSeekerId(user.getId())
                .stream()
                .map(application ->
                        ApplicationResponse.builder()
                                .id(application.getId())
                                .jobId(application.getJob().getId())
                                .jobTitle(application.getJob().getTitle())
                                .applicantName(application.getJobSeeker().getName())
                                .applicantEmail(application.getJobSeeker().getEmail())
                                .resumeUrl(application.getResumeUrl())
                                .status(application.getStatus())
                                .appliedAt(application.getAppliedAt())
                                .build())
                .toList();

    }

    @Override
    public List<ApplicationResponse> getApplicantsForJob(
            Long jobId,
            String recruiterEmail) {

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

        if (!job.getRecruiter().getId()
                .equals(recruiter.getId())) {

            throw new JobOwnershipException(
                    "You are not allowed to view applicants for this job");
        }

        return applicationRepository.findByJobId(jobId)
                .stream()
                .map(application ->
                        ApplicationResponse.builder()
                                .id(application.getId())
                                .jobId(application.getJob().getId())
                                .jobTitle(application.getJob().getTitle())
                                .applicantName(application.getJobSeeker().getName())
                                .applicantEmail(application.getJobSeeker().getEmail())
                                .resumeUrl(application.getResumeUrl())
                                .status(application.getStatus())
                                .appliedAt(application.getAppliedAt())
                                .build())
                .toList();
    }

    @Override
    public ApplicationResponse updateApplicationStatus(
            Long applicationId,
            ApplicationStatus status,
            String recruiterEmail) {

        User recruiter = userRepository
                .findByEmail(recruiterEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Recruiter not found"));

        Application application = applicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Application not found"));

        Job job = application.getJob();

        if (!job.getRecruiter()
                .getId()
                .equals(recruiter.getId())) {

            throw new JobOwnershipException(
                    "You can only manage applications for your own jobs");
        }

        application.setStatus(status);

        Application updatedApplication =
                applicationRepository.save(application);

        return ApplicationResponse.builder()
                .id(updatedApplication.getId())
                .jobId(updatedApplication.getJob().getId())
                .jobTitle(updatedApplication.getJob().getTitle())
                .applicantName(updatedApplication.getJobSeeker().getName())
                .applicantEmail(updatedApplication.getJobSeeker().getEmail())
                .resumeUrl(updatedApplication.getResumeUrl())
                .status(updatedApplication.getStatus())
                .appliedAt(updatedApplication.getAppliedAt())
                .build();
    }

    @Override
    public List<ApplicationResponse> getAllApplicationsForRecruiter(
            String recruiterEmail) {

        User recruiter = userRepository
                .findByEmail(recruiterEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Recruiter not found"));

        return applicationRepository
                .findByJobRecruiterId(recruiter.getId())
                .stream()
                .map(application ->
                        ApplicationResponse.builder()
                                .id(application.getId())
                                .jobId(application.getJob().getId())
                                .jobTitle(application.getJob().getTitle())
                                .applicantName(application.getJobSeeker().getName())
                                .applicantEmail(application.getJobSeeker().getEmail())
                                .resumeUrl(application.getResumeUrl())
                                .status(application.getStatus())
                                .appliedAt(application.getAppliedAt())
                                .build())
                .toList();
    }


}
