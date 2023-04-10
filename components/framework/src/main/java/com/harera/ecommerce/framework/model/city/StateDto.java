package com.harera.ecommerce.framework.model.city;

import java.util.List;

import com.harera.ecommerce.framework.model.BaseEntityDto;

import lombok.Data;

@Data
public class StateDto extends BaseEntityDto {

    private String arabicName;
    private String englishName;
    private List<CityResponse> cities;
}
