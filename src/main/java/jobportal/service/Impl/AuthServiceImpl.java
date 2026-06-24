package jobportal.service.Impl;

import jobportal.dto.request.LoginRequest;
import jobportal.dto.request.RegisterRequest;
import jobportal.entity.Role;
import jobportal.entity.User;
import jobportal.exception.InvalidCredentialsException;
import jobportal.exception.UserNotFoundException;
import jobportal.repository.UserRepository;
import jobportal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String register(RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.JOB_SEEKER)
                .build();

        userRepository.save(user);

        return "User Registered Successfully";
    }

    @Override
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException("Invalid credentials");
        }

        return "Login Successful";
    }

}
