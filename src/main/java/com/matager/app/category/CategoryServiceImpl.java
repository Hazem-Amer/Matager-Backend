package com.matager.app.category;

import com.matager.app.file.FileUploadService;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.store.StoreService;
import com.matager.app.subcategory.SubCategory;
import com.matager.app.subcategory.SubCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Category> categories = categoryRepository.findAllCategoryByStoreId(storeId);
        for (Category category : categories) {
            CategoryModel categoryModel = category.toModel();
            if (categoryModel != null) {
                List<Long> categorySubcategoryIds = subCategoryRepository.findCategorySubCategoryIds(storeId, category.getId());
                if(categorySubcategoryIds !=null) categoryModel.setSubCategoryIds(categorySubcategoryIds);
                categoryModels.add(categoryModel);
            }
        }
        return categoryModels;
    }

    @Override
    public CategoryModel getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
        Store store = storeRepository.findById(category.getStore().getId()).orElseThrow(() -> new RuntimeException("Store not found"));
        subCategoryRepository.findCategorySubCategoryIds(store.getId(),categoryId);
        CategoryModel categoryModel = category.toModel();
        if (categoryModel != null) {
            List<Long> categorySubcategoryIds = subCategoryRepository.findCategorySubCategoryIds(store.getId(), category.getId());
            if(categorySubcategoryIds !=null) categoryModel.setSubCategoryIds(categorySubcategoryIds);
        }
        return categoryModel;
    }


    @Override
    public Category addCategory(Owner owner, Store store, CategoryModel newCategory, MultipartFile imageFile, MultipartFile iconFile) {
        Category category = new Category();
        category.setOwner(owner);
        category.setStore(storeRepository.findById(newCategory.getStoreId()).orElseThrow(() -> new RuntimeException("Store not found")));
        category.setName(newCategory.getName());
        category.setIsVisible(newCategory.getIsVisible());
        String imageUrl = fileUploadService.upload(CATEGORY_IMAGE, imageFile);
        category.setImageUrl(imageUrl);
        String iconUrl = fileUploadService.upload(CATEGORY_ICON, iconFile);
        category.setIconUrl(iconUrl);
        return categoryRepository.saveAndFlush(category);

    }

    @Override
    public CategoryModel updateCategory(Owner owner, Store store, MultipartFile imageFile, MultipartFile iconFile, Long categoryId, CategoryModel newCategory) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found with ID: " + newCategory.getCategoryId()));
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


        if (newCategory.getSubCategoryIds() != null && !newCategory.getSubCategoryIds().isEmpty()) {
            for (Long subCategoryId : newCategory.getSubCategoryIds()) {
                SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new RuntimeException("SubCategory with id : " +subCategoryId+ " not found"));
                if(subCategory.getStore().getId()==category.getStore().getId())
                    subCategoryRepository.updateCategoryId(category.getId(), subCategoryId,  category.getOwner().getId(),category.getStore().getId());
                else
                    throw new RuntimeException("SubCategory with id : " +subCategoryId+ " is not found in the same store of the category: "+category.getId());

            }
            List<Long> categorySubcategoryIds = subCategoryRepository.findCategorySubCategoryIds(category.getStore().getId(), category.getId());
            if(categorySubcategoryIds !=null) categoryModel.setSubCategoryIds(categorySubcategoryIds);
            categoryModel.setSubCategoryIds(categorySubcategoryIds);
        }
        categoryRepository.saveAndFlush(category);
        subCategoryRepository.flush();
        return categoryModel;

    }

    @Override
    public void deleteCategory(Owner owner, Store store, Long categoryId) {
        categoryRepository.deleteById(categoryId);
        categoryRepository.flush();
    }

    private void addSubCategoryIds(CategoryModel categoryModel, Category category) {
        if (categoryModel.getSubCategoryIds() != null && !categoryModel.getSubCategoryIds().isEmpty()) {
            for (Long subCategoryId : categoryModel.getSubCategoryIds()) {
                subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new RuntimeException("SubCategory not found"));
                subCategoryRepository.updateCategoryId(category.getOwner().getId(), category.getStore().getId(), subCategoryId, category.getId());
            }
        }
    }

}
