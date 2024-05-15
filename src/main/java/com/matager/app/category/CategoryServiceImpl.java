package com.matager.app.category;

import com.matager.app.file.FileUploadService;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.store.StoreService;
import com.matager.app.subcategory.SubCategory;
import com.matager.app.subcategory.SubCategoryRepository;
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
    public List<Category> getCategories(Long storeId) {
        return categoryRepository.findAllCategoryByStoreId(storeId);
    }

    @Override
    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("Category not found with ID: " + categoryId));
    }


    @Override
    public Category addCategory(Owner owner, Store store, CategoryModel newCategory, MultipartFile imageFile, MultipartFile iconFile) {
        Category category = new Category();
        category.setOwner(owner);
        category.setStore(storeRepository.findById(newCategory.getStoreId()).orElseThrow(()->new RuntimeException("Store not fount")));
        category.setName(newCategory.getName());
        category.setIsVisible(newCategory.getIsVisible());
        String imageUrl = fileUploadService.upload(CATEGORY_IMAGE, imageFile);
        category.setImageUrl(imageUrl);
        String iconUrl = fileUploadService.upload(CATEGORY_ICON, iconFile);
        category.setIconUrl(iconUrl);
        category = categoryRepository.save(category);

        List<SubCategory> subCategories = new ArrayList<>();
        if (newCategory.getSubCategoryIds() != null && !newCategory.getSubCategoryIds().isEmpty()) {
            subCategories = newCategory.getSubCategoryIds().stream()
                    .map(id -> subCategoryRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("SubCategory not found")))
                    .collect(Collectors.toList());
        }
        category.setSubCategories(subCategories);

        return categoryRepository.saveAndFlush(category);
    }

    @Override
    public Category updateCategory(Owner owner, Store store, MultipartFile imageFile, MultipartFile iconFile, Long categoryId, CategoryModel newCategory) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found with ID: " + newCategory.getCategoryId()));

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
        if (newCategory.getSubCategoryIds() != null) {
            List<SubCategory> subCategories = newCategory.getSubCategoryIds().stream()
                    .map(id -> subCategoryRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("SubCategory not found")))
                    .collect(Collectors.toList());
            category.setSubCategories(subCategories);
        }

        categoryRepository.saveAndFlush(category);
        return category;

    }

    @Override
    public void deleteCategory(Owner owner, Store store, Long categoryId) {
        categoryRepository.deleteById(categoryId);
        categoryRepository.flush();
    }
}
