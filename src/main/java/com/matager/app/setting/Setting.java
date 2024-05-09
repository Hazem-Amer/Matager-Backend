/*
 * @Abdullah Sallam
 */

package com.matager.app.setting;

import com.matager.app.common.domain.BaseEntity;
import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"id", "owner"})
@Table(name = "setting")
public class Setting extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;
}
