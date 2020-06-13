package org.shardingjdbc.manu.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Product {
    private Long productId;

    private String code;

    private String name;

    private String description;

}