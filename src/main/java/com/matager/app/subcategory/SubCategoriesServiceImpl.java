package com.matager.app.subcategory;

import com.matager.app.file.FileType;
import com.matager.app.file.FileUploadService;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.user.User;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.matager.app.file.FileType.SUB_CATEGORY_ICON;

@Service
@RequiredArgsConstructor
public class SubCategoriesServiceImpl implements SubCategoriesService {

    private final SubCategoryRepository subCategoryRepository;
    private final FileUploadService fileUploadService;

    @Override
    public List<SubCategory> getSubCategories(Long storeId) {
        return subCategoryRepository.findSubCategoryByStoreId(storeId);
    }

    @Override
    public SubCategory getSubCategory(Owner owner, User user, Store store, Long subCategoryId) {
        return subCategoryRepository.findById(subCategoryId).orElseThrow(()-> new RuntimeException("SubCategory not found with ID: " + subCategoryId));
    }

    @Override
    public SubCategory addSubCategory(Owner owner, User user, Store store, MultipartFile iconFile, SubCategoryModel newSubCategory) {
        SubCategory subCategory = new SubCategory();
        subCategory.setOwner(owner);
        subCategory.setStore(store);
        subCategory.setName(newSubCategory.getName());
        subCategory.setIsVisible(newSubCategory.getIsVisible());
        String iconUrl = fileUploadService.upload(SUB_CATEGORY_ICON, iconFile);
        subCategory.setCategoryIconUrl(iconUrl);
        subCategoryRepository.saveAndFlush(subCategory);
        return subCategory;
    }

    @Override
    public SubCategory updateSubCategory(Owner owner, Store store, Long subCategoryId, MultipartFile newIconFile, SubCategoryModel newSubCategory) {
        Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(subCategoryId);
        SubCategory subCategory;
        if (optionalSubCategory.isPresent()) {
            subCategory = optionalSubCategory.get();
            subCategory.setOwner(owner);
            subCategory.setStore(store);
            if (newSubCategory.getName() != null) {
                subCategory.setName(newSubCategory.getName());
            }
            if (newSubCategory.getIsVisible() != null) {
                subCategory.setIsVisible(newSubCategory.getIsVisible());
            }
            if (newIconFile != null) {
                String iconUrl = fileUploadService.upload(SUB_CATEGORY_ICON, newIconFile);
                subCategory.setCategoryIconUrl(iconUrl);
            }

            subCategoryRepository.saveAndFlush(subCategory);
            return subCategory;
        } else
            throw new NotFoundException("Category not found with ID: " + newSubCategory.getSubCategoryId());

    }

    @Override
    public void deleteSubCategory(Owner owner, Store store, Long subCategoryId) {
        subCategoryRepository.deleteById(subCategoryId);
        subCategoryRepository.flush();
    }
}

