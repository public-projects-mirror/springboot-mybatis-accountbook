package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.CategoryDTO;
import com.example.entity.CategoryDO;
import com.example.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService extends ServiceImpl<CategoryMapper, CategoryDO> {

    public List<CategoryDTO> getCategoryList() {
        return lambdaQuery()
                .list()
                .stream()
                .map(CategoryDTO.newFromDO())
                .collect(Collectors.toList());
    }

    public CategoryDTO saveCategory(String categoryName) {
        CategoryDO newCategory = new CategoryDO();
        newCategory.setCategoryId(UUID.randomUUID().toString());
        newCategory.setCategoryName(categoryName);
        this.save(newCategory);
        return CategoryDTO.newFromDO().apply(newCategory);
    }

    public CategoryDTO updateCategory(CategoryDO oldCategory, String newCategoryName) {
        oldCategory.setCategoryName(newCategoryName);
        this.updateById(oldCategory);
        return CategoryDTO.newFromDO().apply(oldCategory);
    }

    public void deleteCategory(String categoryName) {
        this.removeById(categoryName);
    }

}
