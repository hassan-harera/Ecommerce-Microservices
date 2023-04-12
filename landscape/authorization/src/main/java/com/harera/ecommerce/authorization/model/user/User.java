package com.harera.ecommerce.authorization.model.user;

import jakarta.persistence.Basic;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.harera.ecommerce.framework.model.BaseEntity;

@Setter
@Getter
@Document(value = "user")
public class User extends BaseEntity implements UserDetails {

    @Field(name = "uid")
    private String uid;

    @Field(name = "username")
    private String username;

    @Field(name = "mobile")
    private String mobile;

    @Field(name = "first_name")
    private String firstName;

    @Field(name = "last_name")
    private String lastName;

    @Field(name = "email")
    private String email;

    @Field(name = "password")
    private String password;

    @Field(name = "device_token")
    private String deviceToken;

    @Field(name = "authorities")
    private List<UserAuthority> authorities;

    public String getUsername() {
        if (username == null)
            return getMobile();
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
