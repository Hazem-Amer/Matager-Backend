package com.matager.app.category;

import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories(Long storeId);

    Category getCategory(Long categoryId);

    Category addCategory(Owner owner, Store store, CategoryModel categoryModel, MultipartFile imageFile, MultipartFile iconFile) ;

    Category updateCategory(Owner owner, Store store, MultipartFile imageFile, MultipartFile iconFile, Long categoryId, CategoryModel categoryModel);

    void deleteCategory(Owner owner, Store store, Long categoryId);
}
