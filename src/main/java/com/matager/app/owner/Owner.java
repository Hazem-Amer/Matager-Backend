/*
 * @Abdullah Sallam
 */

package com.matager.app.owner;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "owner")
public class Owner implements Serializable {

    @Id
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> users;

    @Column(name = "shard_number")
    private Integer shardNum;

    // owner license details and more other properties to add later ...

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner that = (Owner) o;
        return Objects.equals(id, that.id);
    }

}
