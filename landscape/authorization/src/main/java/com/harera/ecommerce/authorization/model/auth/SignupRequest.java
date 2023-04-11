package com.harera.ecommerce.authorization.model.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequest extends SignupDto {

    private String otp;
}
