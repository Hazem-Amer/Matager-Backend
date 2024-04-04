/*
 * @Abdullah Sallam
 */

package com.matager.app.reservation;

import at.orderking.bossApp.common.domain.BaseEntity;
import at.orderking.bossApp.owner.Owner;
import at.orderking.bossApp.store.Store;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"id", "owner", "store"})
@Table(name = "reservation")
public class Reservation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "reservation_no")
    private Long reservationNo; // Reservierung ID column on the POS DB

    @Column(name = "cashier_name")
    private String cashierName;

    @Column(name = "reserver_name")
    private String reserverName; // Reservierung name column on the POS DB

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "`from`")
    private LocalDateTime from;

    @Column(name = "`to`")
    private LocalDateTime to;

    @Column(name = "persons")
    private Integer persons;
}
