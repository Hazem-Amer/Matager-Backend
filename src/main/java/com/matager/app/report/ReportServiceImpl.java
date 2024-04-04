package com.matager.app.report;

import at.orderking.bossApp.auth.AuthenticationFacade;
import at.orderking.bossApp.common.helper.general.JsonUtils;
import at.orderking.bossApp.common.helper.general.PosWebsocketCommand;
import at.orderking.bossApp.common.helper.rest.RestHelper;
import at.orderking.bossApp.owner.Owner;
import at.orderking.bossApp.owner.OwnerService;
import at.orderking.bossApp.setting.SettingService;
import at.orderking.bossApp.setting.Settings;
import com.nimbusds.jose.shaded.json.JSONObject;
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
        RestTemplate restTemplate = new RestTemplateBuilder().build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String authHeader = restHelper.getBasicAuthHeader(username, password);
        headers.add("Authorization", authHeader);

        String sysId = settingService.getSettingValue(storeId, Settings.SYSTEM_ID.getPosKey());

        String reportName = reportRepository.findById(reportId).orElseThrow(() -> new IllegalArgumentException("Report not found.")).getFullName().toString();

        JSONObject body = new JSONObject(Map.of(
                "apiuser", apiUser,
                "apikey", apiKey,
                "systemid", sysId,
                "deviceid", "server",
                "timeout", "30000",
                "name", PosWebsocketCommand.GET_REPORT.getCommand(),
                "type", reportName,
                "from", fromDate.format(df),
                "to", toDate.format(df)
        ));

        log.info("Request body: " + body.toJSONString());

        HttpEntity<String> entity = new HttpEntity<>(body.toJSONString(), headers);

        log.info("Sending request to: " + posWebsocketServerUrl + "/dashboard_api/call");
        log.info("Request body: " + entity.toString());

        try {
            ResponseEntity<String> response = restTemplate.exchange(posWebsocketServerUrl + "/dashboard_api/call", HttpMethod.POST, entity, String.class);
            log.info(response.toString());
            if (response.getStatusCode() == HttpStatus.OK) {
                return JsonUtils.parseJsonAsJSONObject(Objects.requireNonNull(response.getBody())).getJSONObject("ws_response").getString("url");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while generating report." + e.getMessage());
        }

        throw new RuntimeException("Error occurred while generating report.");
    }

}
