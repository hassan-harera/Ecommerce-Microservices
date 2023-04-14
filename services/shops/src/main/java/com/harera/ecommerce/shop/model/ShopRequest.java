package com.harera.ecommerce.shop.model;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "active"})
public class ShopRequest extends ShopDto {

}
