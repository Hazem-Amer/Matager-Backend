package com.matager.app.subcategory;

import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.user.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SubCategoriesService {
    List<SubCategory> getSubCategories(Long storeId);

    SubCategory getSubCategory(Owner owner, User user, Store store, Long subCategoryId);

    SubCategory addSubCategory(Owner owner, User user, Store store, MultipartFile iconFile, SubCategoryModel categoriesModel);

    SubCategory updateSubCategory(Owner owner, Store store, Long subCategoryId, MultipartFile iconFile, SubCategoryModel categoriesModel);

    void deleteSubCategory(Owner owner, Store store, Long subCategoryId);
}
