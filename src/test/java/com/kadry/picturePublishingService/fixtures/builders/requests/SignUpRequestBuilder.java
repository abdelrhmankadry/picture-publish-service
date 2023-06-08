package com.kadry.picturePublishingService.fixtures.builders.requests;

import com.kadry.picturePublishingService.api.models.requests.SignUpRequest;

import static com.kadry.picturePublishingService.fixtures.Utils.asJsonString;

public class SignUpRequestBuilder {

    public static final String EMAIL = "TEST@TEST.com";
    public static final String PASSWORD = "TEST_PASSWORD";

    private String email;
    private String password;
    public static SignUpRequestBuilder aSignUpRequest(){
        return new SignUpRequestBuilder();
    }

    public SignUpRequestBuilder(){
        this.email = EMAIL;
        this.password = PASSWORD;
    }

    public SignUpRequestBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public SignUpRequestBuilder withPassword(String password){
        this.password = password;
        return this;
    }

    public SignUpRequest build(){
        return new SignUpRequest(email, password);
    }

    public String json(){
        return asJsonString(new SignUpRequest(email, password));
    }

}
