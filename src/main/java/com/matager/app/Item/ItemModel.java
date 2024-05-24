package com.matager.app.Item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.matager.app.ItemImage.ItemImage;
import com.matager.app.category.Category;
import com.matager.app.subcategory.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Validated
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ItemModel {

    private Long storeId;

    private Long itemNo;

    private String name;

    private String mainImage;

    private List<Long> imageIds;

    private Double costPrice;

    private Double listPrice;

    private Integer maximumOrderQuantity;

    private Integer minimumOrderQuantity;

    private Double quantity;

    private String skuNumber;

    private Double weight;

    private Long categoryId;

    private Long subcategoryId;

    private String description;

    private boolean isSale;

    private Boolean isVisible;


}
