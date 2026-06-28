package jobportal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApplyJobRequest {

    @NotBlank
    private String resumeUrl;

    //i can remove this


}
