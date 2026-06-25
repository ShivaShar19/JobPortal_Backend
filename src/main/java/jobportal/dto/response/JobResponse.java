package jobportal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class JobResponse {

    private Long id;
    private String title;
    private String companyName;
    private String location;
    private String description;
    private Double salary;
    private String jobType;
    private LocalDateTime createdAt;

}
