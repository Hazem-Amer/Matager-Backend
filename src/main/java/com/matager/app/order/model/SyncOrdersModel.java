package com.matager.app.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@Validated
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SyncOrdersModel {
    private String storeUuid;
    private List<OrderModel> orders;
}
