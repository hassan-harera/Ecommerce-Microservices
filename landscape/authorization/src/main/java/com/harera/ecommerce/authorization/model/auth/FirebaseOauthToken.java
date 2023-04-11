package com.harera.ecommerce.authorization.model.auth;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FirebaseOauthToken implements Serializable {

    private String token;
}
