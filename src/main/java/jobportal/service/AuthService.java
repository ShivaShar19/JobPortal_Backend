package jobportal.service;

import jobportal.dto.request.LoginRequest;
import jobportal.dto.request.RegisterRequest;
import jobportal.dto.response.ApiResponse;
import jobportal.dto.response.AuthResponse;

public interface AuthService {

    ApiResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}
