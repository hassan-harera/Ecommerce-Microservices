package com.harera.ecommerce.framework.model.city;

import com.harera.ecommerce.framework.model.BaseEntityDto;

import lombok.Data;

@Data
public class CityDto extends BaseEntityDto {

    private String arabicName;
    private String englishName;
}
