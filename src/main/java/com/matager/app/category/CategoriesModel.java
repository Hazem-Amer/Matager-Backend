package com.matager.app.category;

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
public class CategoriesModel {
    private long categoryId;
    private String name;
    private Boolean isVisible;
    private String categoryImageUrl;
    private String categoryIconUrl;
}
