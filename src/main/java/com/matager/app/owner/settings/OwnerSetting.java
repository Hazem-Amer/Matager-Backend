package com.matager.app.owner.settings;

import com.matager.app.common.domain.BaseEntity;
import com.matager.app.owner.Owner;
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
@Table(name = "owner_setting")
public class OwnerSetting extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;
}
