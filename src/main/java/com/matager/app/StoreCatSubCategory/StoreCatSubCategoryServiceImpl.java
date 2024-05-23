package com.matager.app.StoreCatSubCategory;

import com.matager.app.category.Category;
import com.matager.app.category.CategoryModel;
import com.matager.app.category.CategoryRepository;
import com.matager.app.subcategory.SubCategory;
import com.matager.app.subcategory.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class StoreCatSubCategoryServiceImpl implements StoreCatSubCategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public StoreCatSubCategoryModel getCategories(Long storeId) {
        List<Category> categories = categoryRepository.findAllCategoryByStoreId(storeId)
                .orElseThrow(() -> new RuntimeException("Store: " + storeId + " has no categories"));
        List<SubCategory> subCategories = subCategoryRepository.findAllByStoreId(storeId)
                .orElseThrow(() -> new RuntimeException("Store: " + storeId + " has no categories"));
        return new StoreCatSubCategoryModel(categories,subCategories);
    }

//    @Override
//    public List<CategoryModel> getCategories(Long storeId) {
//        List<CategoryModel> categoryModels = new ArrayList<>();
//        List<Category> categories = categoryRepository.findAllCategoryByStoreId(storeId)
//                .orElseThrow(() -> new RuntimeException("Store: " + storeId + " has no categories"));
//        for (Category category : categories) {
//            CategoryModel categoryModel = category.toModel();
//            if (categoryModel != null) {
//                List<SubCategory> subCategories = subCategoryRepository.findAllByStoreIdAndCategoryId(storeId, category.getId())
//                        .orElseThrow(() -> new RuntimeException("Category: " + category.getId() + " has no SubCategories"));
//                categoryModel.setSubCategories(subCategories);
//                categoryModels.add(categoryModel);
//            }
//        }
//        return categoryModels;
//    }
}
