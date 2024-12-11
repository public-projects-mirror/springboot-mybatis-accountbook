package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.entity.CategoryDO;
import com.example.model.AddCategoryRequest;
import com.example.model.DeleteCategoryRequest;
import com.example.model.UpdateCategoryRequest;
import com.example.response.ApiResponse;
import com.example.service.CategoryService;
import com.example.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ApiResponse<List<CategoryVO>> list() {
        List<CategoryVO> categoryVOList = categoryService.getCategoryList()
                .stream()
                .map(CategoryVO::new)
                .toList();
        ApiResponse<List<CategoryVO>> apiResponse = new ApiResponse<>();
        apiResponse.setData(categoryVOList);
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("success");
        return apiResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ApiResponse<CategoryVO> add(@RequestBody AddCategoryRequest addCategoryRequest) {
        String categoryName = addCategoryRequest.getCategoryName();
        CategoryDTO categoryDTO = categoryService.saveCategory(categoryName);
        CategoryVO categoryVO = new CategoryVO(categoryDTO);
        ApiResponse<CategoryVO> apiResponse = new ApiResponse<>();
        apiResponse.setData(categoryVO);
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("success");
        return apiResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public ApiResponse<CategoryVO> update(@RequestBody UpdateCategoryRequest updateCategoryRequest) {
        String categoryId = updateCategoryRequest.getCategoryId();
        CategoryDO categoryDO = categoryService.getById(categoryId);
        String categoryName = updateCategoryRequest.getCategoryName();
        CategoryDTO categoryDTO = categoryService.updateCategory(categoryDO, categoryName);
        ApiResponse<CategoryVO> apiResponse = new ApiResponse<>();
        apiResponse.setData(new CategoryVO(categoryDTO));
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("success");
        return apiResponse;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ApiResponse<String> delete(@RequestBody DeleteCategoryRequest deleteCategoryRequest) {
        String categoryId = deleteCategoryRequest.getCategoryId();
        categoryService.deleteCategory(categoryId);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("success");
        return apiResponse;
    }
}
