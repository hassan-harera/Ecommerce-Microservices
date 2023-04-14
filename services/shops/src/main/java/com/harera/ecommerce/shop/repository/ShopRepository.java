package com.harera.ecommerce.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.ecommerce.shop.model.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
