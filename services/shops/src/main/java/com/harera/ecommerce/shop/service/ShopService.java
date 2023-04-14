package com.harera.ecommerce.shop.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.harera.ecommerce.framework.exception.EntityNotFoundException;
import com.harera.ecommerce.framework.util.ObjectMapperUtils;
import com.harera.ecommerce.shop.model.Shop;
import com.harera.ecommerce.shop.model.ShopRequest;
import com.harera.ecommerce.shop.model.ShopResponse;
import com.harera.ecommerce.shop.model.ShopUpdateRequest;
import com.harera.ecommerce.shop.repository.ShopRepository;

@Service
public class ShopService {

    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;
    private final int pageSize;

    public ShopService(ShopRepository shopRepository, ModelMapper modelMapper,
                    @Value("${business.pagination.shops.page-size}") String pageSize) {
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
        this.pageSize = Integer.parseInt(pageSize);

    }

    public List<ShopResponse> list(int page) {
        List<Shop> shops = shopRepository
                        .findAll(Pageable.ofSize(pageSize).withPage(page)).getContent();
        return ObjectMapperUtils.mapAll(shops, ShopResponse.class);
    }

    public ShopResponse get(Long id) {
        Shop shop = shopRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Shop not found"));
        return ObjectMapperUtils.map(shop, ShopResponse.class);
    }

    public ShopResponse create(ShopRequest shopRequest) {
        Shop shop = ObjectMapperUtils.map(shopRequest, Shop.class);
        shopRepository.save(shop);
        return ObjectMapperUtils.map(shop, ShopResponse.class);
    }

    public void update(Long id, ShopUpdateRequest shopUpdateRequest) {
        Shop shop = shopRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Shop not found"));
        modelMapper.map(shopUpdateRequest, shop);
        shopRepository.save(shop);
    }

    public void delete(Long id) {
        shopRepository.deleteById(id);
    }
}
