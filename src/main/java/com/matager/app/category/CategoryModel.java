package com.matager.app.category;

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
    private Long storeId;
    private Long categoryId;
    private String name;
    private Boolean isVisible;
    private String imageUrl;
    private String iconUrl;
    private List<Long> subCategoryIds;
}
