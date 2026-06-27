package jobportal.dto.request;

import jobportal.entity.enums.ApplicationStatus;
import lombok.Data;

@Data
public class UpdateApplicationStatusRequest {

    private ApplicationStatus status;
}
