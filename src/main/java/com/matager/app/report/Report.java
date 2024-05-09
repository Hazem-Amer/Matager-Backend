package com.matager.app.report;

import com.matager.app.common.domain.BaseEntity;
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
@JsonIgnoreProperties({"fullName"})
@Table(name = "report")
public class Report extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "full_name")
    private ReportName fullName;

    @Column(name = "name")
    private String name;

}
