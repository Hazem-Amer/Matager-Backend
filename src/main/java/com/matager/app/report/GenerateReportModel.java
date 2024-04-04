package com.matager.app.report;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class GenerateReportModel {

    Long storeId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss[.SSS][.SS][.S]")
    String from;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss[.SSS][.SS][.S]")
    String to;

    ReportName reportName;
    
}
