package com.harera.ecommerce.framework.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.harera.ecommerce.framework.model.BaseEntityDto;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "active" })
public class BaseUserDto extends BaseEntityDto {

    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String password;
    private String username;
}
