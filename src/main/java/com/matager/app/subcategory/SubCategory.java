package com.matager.app.subcategory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.matager.app.category.Category;
import com.matager.app.common.domain.BaseEntity;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
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
@JsonIgnoreProperties({"owner", "store","category_id"})
@Table(name = "sub_category")
public class SubCategory extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible;

    @Column(name = "icon")
    private String categoryIconUrl;
}
