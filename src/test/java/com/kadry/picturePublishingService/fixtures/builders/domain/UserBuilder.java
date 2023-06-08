package com.kadry.picturePublishingService.fixtures.builders.domain;

import com.kadry.picturePublishingService.domain.Role;
import com.kadry.picturePublishingService.domain.User;

public class UserBuilder {

    public static final String EMAIL = "test@test.com";
    public static final String PASSWORD = "password";

    public static final Role ROLE = Role.USER;
    private String email = EMAIL;
    private String password = PASSWORD;

    private Role role = ROLE;

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withRole(Role role) {
        this.role = role;
        return this;
    }
    public User build() {
        return new User(email, password, role);
    }
}
