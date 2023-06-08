package com.kadry.picturePublishingService.fixtures.builders.requests;

import com.kadry.picturePublishingService.api.models.requests.CredentialsRequest;

import static com.kadry.picturePublishingService.fixtures.Utils.asJsonString;

public class CredentialsRequestBuilder {

    public static final String EMAIL = "TEST@TEST.com";
    public static final String PASSWORD = "TEST_PASSWORD";

    private String email;
    private String password;
    public static CredentialsRequestBuilder aSignUpRequest(){
        return new CredentialsRequestBuilder();
    }
    public static CredentialsRequestBuilder aSignInRequest(){ return new CredentialsRequestBuilder();}
    public CredentialsRequestBuilder(){
        this.email = EMAIL;
        this.password = PASSWORD;
    }

    public CredentialsRequestBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public CredentialsRequestBuilder withPassword(String password){
        this.password = password;
        return this;
    }

    public CredentialsRequest build(){
        return new CredentialsRequest(email, password);
    }

    public String json(){
        return asJsonString(new CredentialsRequest(email, password));
    }

}
