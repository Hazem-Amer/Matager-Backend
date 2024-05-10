/*
 * @Abdullah Sallam
 */

package com.matager.app.owner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.matager.app.common.domain.BaseEntity;
import com.matager.app.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties({"id"})
@Table(name = "owner")
public class Owner extends BaseEntity implements Serializable {

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> users;


    // owner license details and more other properties to add later ...


}
