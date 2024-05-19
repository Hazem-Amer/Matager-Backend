package com.matager.app.ItemImage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.matager.app.Item.Item;
import com.matager.app.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_image")
@JsonIgnoreProperties({"item"})
public class ItemImage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "is_main", nullable = false)
    private Boolean isMain;

}
