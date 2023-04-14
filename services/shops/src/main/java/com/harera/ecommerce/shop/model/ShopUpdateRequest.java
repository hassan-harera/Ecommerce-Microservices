package com.harera.ecommerce.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true, value = { "id", })
public class ShopUpdateRequest extends ShopDto {

}
