package com.matager.app.category;

import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.user.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoriesService {
    List<Category> getCategories(Long storeId);

    CategoriesModel getCategory(Long categoryId);


    Category addCategory(Owner owner, Store store, CategoriesModel categoryModel, MultipartFile imageFile, MultipartFile iconFile) throws Exception;

    Category updateCategory(Owner owner, Store store, MultipartFile imageFile, MultipartFile iconFile, Long categoryId, CategoriesModel categoriesModel) throws Exception;

    void deleteCategory(Owner owner, Store store, Long categoryId);
}
