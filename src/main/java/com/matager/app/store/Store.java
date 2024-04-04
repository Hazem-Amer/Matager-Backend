/*
 * @Abdullah Sallam
 */

package com.matager.app.store;

import at.orderking.bossApp.Item.Item;
import at.orderking.bossApp.common.domain.BaseEntity;
import at.orderking.bossApp.domain.cashbook.Cashbook;
import at.orderking.bossApp.employee.Employee;
import at.orderking.bossApp.employee.log.EmployeeLog;
import at.orderking.bossApp.order.Order;
import at.orderking.bossApp.order.delivery.DeliveryCustomer;
import at.orderking.bossApp.owner.Owner;
import at.orderking.bossApp.reservation.Reservation;
import at.orderking.bossApp.setting.Setting;
import at.orderking.bossApp.visit.Visit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private List<Employee> employees;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EmployeeLog> employeeLogs;

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
    private List<Visit> visits;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Cashbook> cashbookRecords;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Setting> settings;

}
