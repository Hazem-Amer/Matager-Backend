package com.matager.app.StoreCatSubCategory;

import com.matager.app.category.CategoryModel;

import java.util.List;

public interface StoreCatSubCategoryService {
    List<CategoryModel> getCategories(Long storeId);

}
