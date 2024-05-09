package com.matager.app.common.statistics.dto.general;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class DateNameCountAmountDto {
    String date;
    String name;
    Integer count;
    Double amount;
}
