package com.matager.app.report;

import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.common.helper.general.JsonUtils;
import com.matager.app.common.helper.rest.RestHelper;
import com.matager.app.owner.Owner;
import com.matager.app.owner.OwnerService;
import com.matager.app.setting.SettingService;
import com.matager.app.setting.Settings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final AuthenticationFacade authenticationFacade;
    private final ReportRepository reportRepository;
    private final RestHelper restHelper;
    private final SettingService settingService;
    private final OwnerService ownerService;
    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Value("${pos-websocket-server.url}")
    private String posWebsocketServerUrl;

    @Value("${pos-websocket-server.username}")
    private String username;

    @Value("${pos-websocket-server.password}")
    private String password;

    @Value("${pos-websocket-server.api-user}")
    private String apiUser;

    @Value("${pos-websocket-server.api-key}")
    private String apiKey;

    @Override
    public void initializeReports() {
        reportRepository.deleteAll();

        ReportName[] reportNames = ReportName.values();

        for (ReportName reportName : reportNames) {
            Report report = new Report();
            report.setFullName(reportName);
            report.setName(reportName.getName());
            reportRepository.save(report);
        }
    }


    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public String generateReport(LocalDate fromDate, LocalDate toDate, Long storeId, Long reportId) {
        Owner owner = authenticationFacade.getAuthenticatedUser().getOwner();

        if (!ownerService.hasStore(owner.getId(), storeId)) {
            throw new IllegalArgumentException("Store not found.");
        }

        return getReportUrlFromPos(fromDate, toDate, storeId, reportId);
    }

    public String getReportUrlFromPos(LocalDate fromDate, LocalDate toDate, Long storeId, Long reportId) {


        throw new RuntimeException("Error occurred while generating report.");
    }

}
