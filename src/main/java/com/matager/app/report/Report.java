package com.matager.app.report;

import at.orderking.bossApp.common.domain.BaseEntity;
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
@JsonIgnoreProperties({"fullName"})
@Table(name = "report")
public class Report extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "full_name")
    private ReportName fullName;

    @Column(name = "name")
    private String name;

}
