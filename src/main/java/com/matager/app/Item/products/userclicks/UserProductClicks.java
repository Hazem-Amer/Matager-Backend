package com.matager.app.Item.products.userclicks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.matager.app.Item.Item;
import com.matager.app.common.domain.BaseEntity;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({""})
@Table(name = "user_product_click")
public class UserProductClicks extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "click_count")
    private int clickCount;


}
