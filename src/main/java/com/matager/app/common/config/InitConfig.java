/*
 * @Abdullah Sallam
 */

package com.matager.app.common.config;

import at.orderking.bossApp.common.config.db.sharding.DBContextHolder;
import at.orderking.bossApp.common.config.db.sharding.DBShardEnum;
import at.orderking.bossApp.common.config.db.stored_procedures.StoredProcedureUtil;
import at.orderking.bossApp.report.ReportService;
import at.orderking.bossApp.widget.WidgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InitConfig {

    @Value("${config.default-data-initialization}")
    private boolean initialize;

    private final WidgetService widgetService;
    private final StoredProcedureUtil storedProcedureUtil;
    private final ReportService reportService;

    // init widgets
    @Bean
    CommandLineRunner initCongregation() {
        return args -> {
            if (initialize) {
                for (DBShardEnum shard : DBShardEnum.values()) {
                    DBContextHolder.setCurrentDb(shard);
                    initWidgets();
                    intiStoredProcedures();
                    initReports();
                }
            }
        };
    }

    // init widgets
    private void initWidgets() {
        widgetService.initializeWidgets();
    }

    // init stored procedures
    private void intiStoredProcedures() {
        storedProcedureUtil.initStoredProcedures();
    }

    // init reports
    private void initReports() {
        reportService.initializeReports();
    }


}


