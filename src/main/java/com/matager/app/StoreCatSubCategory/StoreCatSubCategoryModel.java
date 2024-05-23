package com.matager.app.StoreCatSubCategory;

import com.matager.app.category.Category;
import com.matager.app.subcategory.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import java.util.List;
@Data
@Validated
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StoreCatSubCategoryModel {
    List<Category> categories;
    List<SubCategory> subCategories;
}
