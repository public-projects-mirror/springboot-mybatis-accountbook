package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.CategoryDTO;
import com.example.entity.CategoryDO;
import com.example.exception.CategoryAlreadyExistsException;
import com.example.exception.CategoryNotFoundException;
import com.example.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService extends ServiceImpl<CategoryMapper, CategoryDO> {

    @Transactional
    public List<CategoryDTO> getCategoryList() {
        return lambdaQuery()
                .list()
                .stream()
                .map(CategoryDTO.newFromDO())
                .collect(Collectors.toList());
    }

    public Boolean CategoryExist(String categoryName) {
        LambdaQueryWrapper<CategoryDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CategoryDO::getCategoryName, categoryName);
        List<CategoryDO> categoryDOS = this.baseMapper.selectList(lambdaQueryWrapper);
        return !categoryDOS.isEmpty();
    }

    @Transactional
    public CategoryDTO saveCategory(String categoryName) {
        if (CategoryExist(categoryName)) {
            throw new CategoryAlreadyExistsException(categoryName);
        }
        CategoryDO newCategory = new CategoryDO();
        newCategory.setCategoryId(UUID.randomUUID().toString());
        newCategory.setCategoryName(categoryName);
        this.save(newCategory);
        return CategoryDTO.newFromDO().apply(newCategory);
    }

    @Transactional
    public CategoryDTO updateCategory(CategoryDO oldCategory, String newCategoryName) {
        if (oldCategory == null) {
            throw new CategoryNotFoundException("I can't find old category");
        }
        oldCategory.setCategoryName(newCategoryName);
        this.updateById(oldCategory);
        return CategoryDTO.newFromDO().apply(oldCategory);
    }

    @Transactional
    public void deleteCategory(String categoryName) {
        this.removeById(categoryName);
    }

}
