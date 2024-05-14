package com.matager.app.subcategory;

import com.matager.app.category.CategoriesModel;
import com.matager.app.category.Category;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SubCategoriesService {
    List<SubCategory> getSubCategories(Owner owner, User user, Store store);
    SubCategory getSubCategory(Owner owner, User user, Store store,long subCategoryId);

    SubCategory addSubCategory(Owner owner, User user, Store store, SubCategoryModel categoriesModel);

    SubCategory updateSubCategory(Owner owner, Store store, long subCategoryId,SubCategoryModel categoriesModel);
    void deleteSubCategory(Owner owner, Store store,long subCategoryId);
}
