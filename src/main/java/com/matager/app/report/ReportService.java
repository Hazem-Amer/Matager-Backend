package com.matager.app.report;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    void initializeReports();

    List<Report> getAllReports();

    String generateReport(LocalDate fromDate, LocalDate toDate, Long storeId, Long reportId);

}
