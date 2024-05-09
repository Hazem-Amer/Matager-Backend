package com.matager.app.common.statistics.dto.general;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class NameCountDAmountDto {
    String name;
    Double count;
    Double amount;
}
