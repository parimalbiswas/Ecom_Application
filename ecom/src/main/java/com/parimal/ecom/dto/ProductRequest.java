package com.parimal.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String name;
    private String description;
    private String price;
    private Integer stockQuantity;
    private String category;
    private String imageUrl;
    private Boolean active = true;
}
