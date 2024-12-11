package com.example.dto;

import com.example.entity.AccountDO;
import com.example.entity.CategoryDO;

import java.util.function.Function;

public class CategoryDTO {
    private String categoryId;
    private String categoryName;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public static Function<CategoryDO, CategoryDTO> newFromDO() {
        return new Function<CategoryDO, CategoryDTO>() {
            @Override
            public CategoryDTO apply(CategoryDO categoryDO) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setCategoryId(categoryDO.getCategoryId());
                categoryDTO.setCategoryName(categoryDO.getCategoryName());
                return categoryDTO;
            }
        };
    }
}
