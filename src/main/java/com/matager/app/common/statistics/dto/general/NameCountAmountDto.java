package com.matager.app.common.statistics.dto.general;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class NameCountAmountDto {
    String name;
    Integer count;
    Double amount;
}
