package jobportal.dto.response;

import jobportal.entity.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class RecruiterDashboardResponse {

    private Long totalJobs;

    private Long totalApplications;

    private Map<ApplicationStatus, Long> applicationsByStatus;

    private List<ApplicationResponse> recentApplications;

}
