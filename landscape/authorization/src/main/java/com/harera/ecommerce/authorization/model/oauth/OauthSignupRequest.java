package com.harera.ecommerce.authorization.model.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.ecommerce.authorization.model.auth.SignupDto;

import lombok.Data;

@Data
public class OauthSignupRequest extends SignupDto {

    @JsonProperty("oauth_token")
    private String oauthToken;
}
