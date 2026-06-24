package jobportal.service;

import jobportal.dto.request.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);

//    String login(LoginRequest request);

}
