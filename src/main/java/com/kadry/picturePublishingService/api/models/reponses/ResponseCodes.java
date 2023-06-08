package com.kadry.picturePublishingService.api.models.reponses;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum ResponseCodes {
    SIGNUP_SUCCEEDED(100),
    SIGNUP_FAILED(101),
    LOGIN_SUCCEEDED(102),
    LOGIN_FAILED(103),
    LOGOUT_SUCCEEDED(104),
    LOGOUT_FAILED(105),
    ;
    final int code;
}
