package jobportal.dto.response;

import jobportal.entity.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApplicationResponse {

    private Long id;

    private Long jobId;

    private String jobTitle;

    private String companyName;

    private String applicantName;

    private String applicantEmail;

    private String resumeUrl;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;

}
