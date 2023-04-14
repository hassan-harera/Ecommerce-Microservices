package com.harera.ecommerce.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harera.ecommerce.shop.model.ShopRequest;
import com.harera.ecommerce.shop.model.ShopResponse;
import com.harera.ecommerce.shop.model.ShopUpdateRequest;
import com.harera.ecommerce.shop.service.ShopService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/shops")
@Tag(name = "Shops", description = "Shop API")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping
    @Operation(summary = "List", description = "List all shops", tags = "Shops",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<ShopResponse>> list(
                    @RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok().body(shopService.list(page));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get shop by id", tags = "Shops",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|Ok"),
                            @ApiResponse(responseCode = "404",
                                            description = "error|Not Found"),
                            @ApiResponse(responseCode = "401",
                                            description = "error|Unauthorized"),
                            @ApiResponse(responseCode = "403",
                                            description = "error|Forbidden") })
    public ResponseEntity<ShopResponse> get(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(shopService.get(id));
    }

    // TODO : use authorization method
    @PostMapping
    @Operation(summary = "Create", description = "Create shop by id", tags = "Shops",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<ShopResponse> create(@RequestBody ShopRequest shopRequest) {
        return ResponseEntity.ok(shopService.create(shopRequest));
    }

    // TODO : use authorization method
    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Update shop by id", tags = "Shops",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<Void> update(@PathVariable(value = "id") Long id,
                    @RequestBody ShopUpdateRequest shopUpdateRequest) {
        shopService.update(id, shopUpdateRequest);
        return ResponseEntity.ok().build();
    }

    // TODO : use authorization method
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete", description = "Delete shop by id", tags = "Shops",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<Void> deleteShop(@PathVariable(value = "id") Long id) {
        shopService.delete(id);
        return ResponseEntity.ok().build();
    }
}
