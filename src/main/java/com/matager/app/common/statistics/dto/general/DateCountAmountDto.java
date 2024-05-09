package com.matager.app.common.statistics.dto.general;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class DateCountAmountDto {
    String date;
    Integer count;
    Double amount;
}
