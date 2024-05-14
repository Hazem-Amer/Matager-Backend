package com.matager.app.category;

import com.matager.app.ItemImage.ItemImageRepository;
import com.matager.app.file.FileType;
import com.matager.app.file.FileUploadService;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.subcategory.SubCategory;
import com.matager.app.user.User;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.matager.app.file.FileType.CATEGORY_ICON;
import static com.matager.app.file.FileType.CATEGORY_PHOTO;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;


    @Override
    public List<Category> getCategories(Owner owner, User user, Store store) {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(Owner owner, User user, Store store, long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent())
            return optionalCategory.get();
        else
            throw new NotFoundException("Category not found with ID: " + categoryId);
    }

    @Override
    public Category addCategory(Owner owner, User user, Store store, CategoriesModel newCategory) {
        Category category = new Category();
        category.setOwner(owner);
        category.setStore(store);
        category.setName(newCategory.getName());
        category.setIsVisible(newCategory.getIsVisible());
        category = categoryRepository.saveAndFlush(category);
        return category;
    }

    @Override
    public Category addCategory(Owner owner, Store store, CategoriesModel newCategory, MultipartFile imageFile, MultipartFile iconFile) throws Exception {
        Category category = new Category();
        category.setOwner(owner);
        category.setStore(store);
        category.setName(newCategory.getName());
        category.setIsVisible(newCategory.getIsVisible());
        String imageUrl = fileUploadService.upload(CATEGORY_PHOTO, imageFile);
        category.setCategoryImageUrl(imageUrl);
        String iconUrl = fileUploadService.upload(CATEGORY_ICON, iconFile);
        category.setCategoryIconUrl(iconUrl);
        category = categoryRepository.save(category);
        return category;
    }

    @Override
    public Category updateCategory(Owner owner, Store store, MultipartFile imageFile, MultipartFile iconFile, Long categoryId, CategoriesModel newCategory) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        Category category;
        if (optionalCategory.isPresent()) {
            category = optionalCategory.get();
            category.setOwner(owner);
            category.setStore(store);
            if (newCategory.getName() != null) {
                category.setName(newCategory.getName());
            }
            if (newCategory.getIsVisible() != null) {
                category.setIsVisible(newCategory.getIsVisible());
            }
            if (imageFile != null) {
                String imageUrl = fileUploadService.upload(CATEGORY_PHOTO, imageFile);
                category.setCategoryImageUrl(imageUrl);
            }

            if (iconFile != null) {
                String iconUrl = fileUploadService.upload(CATEGORY_ICON, iconFile);
                category.setCategoryIconUrl(iconUrl);
            }

            categoryRepository.saveAndFlush(category);
            return category;
        } else
            throw new NotFoundException("Category not found with ID: " + newCategory.getCategoryId());

    }

    @Override
    public void deleteCategory(Owner owner, Store store, Long categoryId) {
        categoryRepository.deleteById(categoryId);
        categoryRepository.flush();
    }
}
