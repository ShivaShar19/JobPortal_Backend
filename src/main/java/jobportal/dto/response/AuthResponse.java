package jobportal.dto.response;

import jobportal.entity.enums.Role;

public record AuthResponse(String token, Role role) {
}
