/*
 * @Abdullah Sallam
 */

package com.matager.app.store;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matager.app.Item.Item;
import com.matager.app.common.domain.BaseEntity;
import com.matager.app.order.Order;
import com.matager.app.order.delivery.DeliveryCustomer;
import com.matager.app.owner.Owner;
import com.matager.app.setting.Setting;
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
@Table(name = "store")
public class Store extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnore
    private Owner owner;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "brand")
    private String brand;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "address")
    private String address; //?


    // Store related entities

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Item> items;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DeliveryCustomer> deliveryCustomers;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;



    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Setting> settings;

}
