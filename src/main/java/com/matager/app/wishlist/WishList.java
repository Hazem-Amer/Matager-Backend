package com.matager.app.wishlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.matager.app.common.domain.BaseEntity;
import com.matager.app.store.Store;
import com.matager.app.user.User;
import com.matager.app.wishlist.wishlist_item.WishListItem;
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
@JsonIgnoreProperties({"store","user"})
@Table(name = "wish_list")
public class WishList extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "wishList", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<WishListItem> wishListItems;
}
