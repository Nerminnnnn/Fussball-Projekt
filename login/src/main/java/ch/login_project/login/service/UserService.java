package ch.login_project.login.service;

import ch.login_project.login.models.UserEntity;

import ch.login_project.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void updateUsername(String currentUsername, String newUsername) {
        UserEntity user = userRepository.findByUsername(currentUsername).orElse(null);
        if (user != null) {
            user.setUsername(newUsername);
            userRepository.save(user);
        }
    }

    public void updatePassword(String currentUsername, String newPassword) {
        UserEntity user = userRepository.findByUsername(currentUsername).orElse(null);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
    }
}
