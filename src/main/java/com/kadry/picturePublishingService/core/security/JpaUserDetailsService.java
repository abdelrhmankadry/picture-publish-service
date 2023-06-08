package com.kadry.picturePublishingService.core.security;

import com.kadry.picturePublishingService.domain.user.User;
import com.kadry.picturePublishingService.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User with email " + email + " not found"));

        return new org.springframework.security.core.userdetails.
                User(user.getEmail(), user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().toString())));
    }
}
