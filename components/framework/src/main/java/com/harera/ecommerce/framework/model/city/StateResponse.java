package com.harera.ecommerce.framework.model.city;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"active"})
public class StateResponse extends StateDto {
}
