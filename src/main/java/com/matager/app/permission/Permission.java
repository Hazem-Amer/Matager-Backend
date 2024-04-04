/*
 * @Abdullah Sallam
 */

package com.matager.app.permission;

import at.orderking.bossApp.common.domain.BaseEntity;
import at.orderking.bossApp.owner.Owner;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"id", "owner"})
@Table(name = "permission")
public class Permission extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @Column(name = "name")
    private String name;
}
