package com.matager.app.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CategoryModel {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long storeId;
    private Long categoryId;
    private String name;
    private Boolean isVisible;
    private String imageUrl;
    private String iconUrl;
    private List<Long> subCategoryIds;
    private List<SubCategory> subCategories;
}
