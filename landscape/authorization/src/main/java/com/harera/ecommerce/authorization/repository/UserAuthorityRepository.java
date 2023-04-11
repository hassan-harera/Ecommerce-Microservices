package com.harera.ecommerce.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harera.ecommerce.authorization.model.user.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

}
