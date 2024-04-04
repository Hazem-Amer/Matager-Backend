/*
 * @Abdullah Sallam
 */

package com.matager.app.common.aspect;

import at.orderking.bossApp.auth.AuthenticationFacade;
import at.orderking.bossApp.common.config.db.sharding.DBContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class DBShardingAspect {

    private final AuthenticationFacade authenticationFacade;

//    @Pointcut("execution(* at.orderking.bossApp.repository.*.*(..))")
//    public void queryExecution() {
//    }

    @Pointcut("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))")
    public void queryExecution() {
    }

    // Runs before any query execution
    @Before("queryExecution()")
    public void beforeQueryExecution() {
        try {
            DBContextHolder.setCurrentDb(Integer.parseInt(authenticationFacade.getJwt().getSubject().split("\\.")[1]));
        } catch (Exception e) {
            log.debug("No Authenticated User Found, No Auto Sharding logic happened.");
        }
    }


}
