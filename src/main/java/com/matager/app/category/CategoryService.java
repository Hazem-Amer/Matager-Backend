package com.matager.app.category;

import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import org.jvnet.hk2.annotations.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    List<CategoryModel> getCategories(Long storeId);
    List<Category> getStoreCategories(Long storeId);

    CategoryModel getCategory(Long categoryId);

    CategoryModel addCategory(Owner owner, Store store, CategoryModel categoryModel, MultipartFile imageFile, MultipartFile iconFile) ;

    CategoryModel updateCategory(Owner owner, Store store, MultipartFile imageFile, MultipartFile iconFile, Long categoryId, CategoryModel categoryModel);

    void deleteCategory(Long categoryId);
}
