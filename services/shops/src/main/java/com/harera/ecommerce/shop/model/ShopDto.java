package com.harera.ecommerce.shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.ecommerce.framework.model.BaseEntityDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopDto extends BaseEntityDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;
}
