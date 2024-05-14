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
public class CategoriesModel {
    private long categoryId;
    private String name;
    private Boolean isVisible;
    private String categoryImageUrl;
    private String categoryIconUrl;
    private List<Long> subCategoryIds;
}
