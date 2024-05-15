package com.matager.app.subcategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryModel {
    private Long storeId;
    private Long subCategoryId;
    private String name;
    private Boolean isVisible;
    private String subCategoryIconUrl;
}
