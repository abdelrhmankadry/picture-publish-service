package com.kadry.picturePublishingService.core;

import com.kadry.picturePublishingService.domain.user.Role;
import com.kadry.picturePublishingService.domain.user.User;
import com.kadry.picturePublishingService.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BootstrappingService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @PostConstruct
    public void addAdminAccount(){
        if(userRepository.findUserByEmail("admin@admin.com").isEmpty()) {
            User admin = new User("admin@admin.com", passwordEncoder.encode("admin123")
                    , Role.ROLE_ADMIN);
            userRepository.save(admin);
        }
    }
}
