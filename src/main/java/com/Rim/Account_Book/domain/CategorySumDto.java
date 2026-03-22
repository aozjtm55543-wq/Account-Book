package com.Rim.Account_Book.domain;

import lombok.Getter;

@Getter
public class CategorySumDto {
    private String category;
    private Long totalAmount;

    public CategorySumDto(String category, Long totalAmount) {
        this.category = category;
        this.totalAmount = totalAmount;
    }
}
