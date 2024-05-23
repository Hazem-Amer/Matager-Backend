package com.matager.app.category;

import com.matager.app.file.FileUploadService;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.subcategory.SubCategory;
import com.matager.app.subcategory.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.matager.app.file.FileType.CATEGORY_ICON;
import static com.matager.app.file.FileType.CATEGORY_IMAGE;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;
    private final SubCategoryRepository subCategoryRepository;
    private final StoreRepository storeRepository;

    @Override
    public List<CategoryModel> getCategories(Long storeId) {
        List<CategoryModel> categoryModels = new ArrayList<>();
        List<Category> categories = categoryRepository.findAllCategoryByStoreId(storeId)
                .orElseThrow(() -> new RuntimeException("Store: " + storeId + " has no categories"));
        for (Category category : categories) {
            CategoryModel categoryModel = category.toModel();
            if (categoryModel != null) {
                List<SubCategory> subCategories = subCategoryRepository.findAllByStoreIdAndCategoryId(storeId, category.getId())
                        .orElseThrow(() -> new RuntimeException("Category: " + category.getId() + " has no SubCategories"));
                categoryModel.setSubCategories(subCategories);
                categoryModels.add(categoryModel);
            }
        }
        return categoryModels;
    }

    @Override
    public CategoryModel getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
        Store store = storeRepository.findById(category.getStore().getId()).orElseThrow(() -> new RuntimeException("Store not found"));
        subCategoryRepository.findCategorySubCategoryIds(store.getId(), categoryId);
        CategoryModel categoryModel = category.toModel();
        if (categoryModel != null) {
            List<SubCategory> subCategories = subCategoryRepository.findAllByStoreIdAndCategoryId(store.getId(), category.getId())
                    .orElseThrow(() -> new RuntimeException("Category: " + categoryId + " has no SubCategories"));
            categoryModel.setSubCategories(subCategories);
        }
        return categoryModel;
    }


    @Override
    public CategoryModel addCategory(Owner owner, Store store, CategoryModel newCategory, MultipartFile imageFile, MultipartFile iconFile) {
        Category category = new Category();
        CategoryModel categoryModel;
        category.setOwner(owner);
        category.setStore(storeRepository.findById(newCategory.getStoreId()).orElseThrow(() -> new RuntimeException("Store not found")));
        category.setName(newCategory.getName());
        category.setIsVisible(newCategory.getIsVisible());
        String imageUrl = fileUploadService.upload(CATEGORY_IMAGE, imageFile);
        category.setImageUrl(imageUrl);
        String iconUrl = fileUploadService.upload(CATEGORY_ICON, iconFile);
        category.setIconUrl(iconUrl);
        category = categoryRepository.saveAndFlush(category);
        categoryModel = category.toModel();
        return categoryModel;
    }

    @Override
    public CategoryModel updateCategory(Owner owner, Store store, MultipartFile imageFile, MultipartFile iconFile, Long categoryId, CategoryModel newCategory) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
        CategoryModel categoryModel = category.toModel();
        if (newCategory.getName() != null) {
            category.setName(newCategory.getName());
        }
        if (newCategory.getIsVisible() != null) {
            category.setIsVisible(newCategory.getIsVisible());
        }
        if (imageFile != null) {
            String imageUrl = fileUploadService.upload(CATEGORY_IMAGE, imageFile);
            category.setImageUrl(imageUrl);
        }

        if (iconFile != null) {
            String iconUrl = fileUploadService.upload(CATEGORY_ICON, iconFile);
            category.setIconUrl(iconUrl);
        }


        category = categoryRepository.saveAndFlush(category);
        assignSubCategoryIdsToCategory(category, newCategory, categoryModel);
        subCategoryRepository.flush();
        return categoryModel;

    }

    private void assignSubCategoryIdsToCategory(Category category, CategoryModel newCategory, CategoryModel categoryModel) {
        if (newCategory.getSubCategoryIds() != null && !newCategory.getSubCategoryIds().isEmpty()) {
            for (Long subCategoryId : newCategory.getSubCategoryIds()) {
                SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new RuntimeException("SubCategory with id : " + subCategoryId + " not found"));
                if (subCategory.getStore().getId() == category.getStore().getId())
                    subCategoryRepository.updateCategoryById(category.getId(), subCategoryId, category.getOwner().getId(), category.getStore().getId());
                else
                    throw new RuntimeException("SubCategory with id : " + subCategoryId + " is not found in the same store of this category: ");

            }
            List<SubCategory> subCategories = subCategoryRepository.findAllByStoreIdAndCategoryId(category.getStore().getId(), category.getId())
                    .orElseThrow(() -> new RuntimeException("Category with id : " + category.getId() + " has no SubCategories"));
            categoryModel.setSubCategories(subCategories);
        }
    }
    @Override
    public void deleteCategory(Long categoryId) {
        subCategoryRepository.removeCategoryIdById(categoryId);
        categoryRepository.deleteById(categoryId);
        categoryRepository.flush();
    }

    private void addSubCategoryIds(CategoryModel categoryModel, Category category) {
        if (categoryModel.getSubCategoryIds() != null && !categoryModel.getSubCategoryIds().isEmpty()) {
            for (Long subCategoryId : categoryModel.getSubCategoryIds()) {
                subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new RuntimeException("SubCategory not found"));
                subCategoryRepository.updateCategoryById(category.getOwner().getId(), category.getStore().getId(), subCategoryId, category.getId());
            }
        }
    }

}
