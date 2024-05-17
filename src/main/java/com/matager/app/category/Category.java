package com.matager.app.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.matager.app.ItemImage.ItemImage;
import com.matager.app.common.domain.BaseEntity;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.subcategory.SubCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"owner", "store"})
@Table(name = "category")
public class Category extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible;

    @Column(name = "image")
    private String imageUrl;

    @Column(name = "icon")
    private String iconUrl;
    public CategoryModel toModel() {
        return CategoryModel.builder()
                .storeId(this.store != null ? this.store.getId() : null)
                .categoryId(this.getId())
                .name(this.name)
                .isVisible(this.isVisible)
                .imageUrl(this.imageUrl)
                .iconUrl(this.iconUrl)
                .build();
    }

}
