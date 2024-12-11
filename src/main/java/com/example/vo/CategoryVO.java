package com.example.vo;

import com.example.dto.CategoryDTO;

public class CategoryVO {
    private transient final CategoryDTO categoryDTO;

    public CategoryVO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public String getCategoryId() {
        return categoryDTO.getCategoryId();
    }

    public String getCategoryName() {
        return categoryDTO.getCategoryName();
    }
}
