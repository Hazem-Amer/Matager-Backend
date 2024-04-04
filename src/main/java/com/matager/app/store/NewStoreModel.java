package com.matager.app.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class NewStoreModel {
    private String name;
    private String brand;
    private String address;
}
