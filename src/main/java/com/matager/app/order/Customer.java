package com.matager.app.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.matager.app.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "'customer'")
@JsonIgnoreProperties({"id", "owner", "store"})
public class Customer extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "country")
    private String country;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "zone_name")
    private String zoneName;

    @Column(name = "building")
    private String building;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "block_no")
    private String blockNo;

    @Column(name = "floor_no")
    private String floorNo;

    @Column(name = "notes")
    private String notes;

    @Column(name = "address")
    private String fullAddress;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;


}
