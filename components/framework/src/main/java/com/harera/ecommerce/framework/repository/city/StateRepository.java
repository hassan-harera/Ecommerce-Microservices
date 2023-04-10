package com.harera.ecommerce.framework.repository.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.ecommerce.framework.model.city.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
}
