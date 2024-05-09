package com.matager.app.report;

import com.matager.app.common.helper.res_model.ResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Map;

@RequestMapping("/v1/report")
@RequiredArgsConstructor
@RestController
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<?> getReports() {
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Reports fetched successfully.")
                        .data(Map.of("reports", reportService.getAllReports()))
                        .build());
    }



    @GetMapping("/generate")
    public ResponseEntity<?> generateReport(@RequestParam(name = "storeId") Long storeId,
                                            @RequestParam(name = "reportId") Long reportId,
                                            @RequestParam(name = "from") @DateTimeFormat(pattern = "[dd.MM.yyyy][dd-MM-yyyy]") @PastOrPresent LocalDate fromDate,
                                            @RequestParam(name = "to") @DateTimeFormat(pattern = "[dd.MM.yyyy][dd-MM-yyyy]") @PastOrPresent LocalDate toDate) {
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Report generated successfully.")
                        .data(Map.of("reportUrl", reportService.generateReport(fromDate, toDate, storeId, reportId)))
                        .build());
    }



}
