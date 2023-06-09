package com.kadry.picturePublishingService.core.security;

import com.kadry.picturePublishingService.domain.user.Role;

import java.util.List;

public record AuthenticatedUser(
        String email,
        List<Role> roles
) {
}
