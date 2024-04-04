/*
 * @Abdullah Sallam
 */

package com.matager.app.user;


import at.orderking.bossApp.common.domain.BaseEntity;
import at.orderking.bossApp.owner.Owner;
import at.orderking.bossApp.store.Store;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"id", "owner", "enabled", "password"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "enabled", columnDefinition = "boolean DEFAULT true")
    private boolean enabled = true;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnore
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonIgnore
    private Store defaultStore;

    @Column(name = "name")
    private String name;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

//    @OneToMany
//    private Set<Permission> permissions;

}
