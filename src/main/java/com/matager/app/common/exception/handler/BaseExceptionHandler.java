/*
 * @Abdullah Sallam
 */

package com.matager.app.common.exception.handler;

import at.orderking.bossApp.common.helper.res_model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseModel> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ex.printStackTrace();

        return ResponseEntity.badRequest().body(
                ResponseModel.builder()
                        .statusCode(BAD_REQUEST.value())
                        .status(BAD_REQUEST)
                        .message("INPUT DATA IS NOT VALID.")
                        .reason(
                                ex.getFieldErrors()
                                        .stream()
                                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                        .collect(Collectors.joining(",")))
                        .data(
                                Map.of("errors",
                                        ex.getBindingResult()
                                                .getFieldErrors()
                                                .stream()
                                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                                .collect(Collectors.toList())))
                        .build());

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseModel> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ex.printStackTrace();


        return ResponseEntity.badRequest().body(
                ResponseModel.builder()
                        .statusCode(BAD_REQUEST.value())
                        .status(BAD_REQUEST)
                        .message("Type mismatch. Check request data format.")
                        .reason("Error parsing value: " + ex.getValue())
                        .build());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseModel> handleSQLException(SQLException ex) {

        return ResponseEntity.badRequest().body(
                ResponseModel.builder()
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message("Query: " + ex.getSQLState() + "\n First: " + ex.getMessage() + "\n Second: " + ex.getNextException().getMessage())
                        .reason(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseModel> handleException(Exception ex) {
        ex.printStackTrace();

        return ResponseEntity.badRequest().body(
                ResponseModel.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(BAD_REQUEST.value())
                        .status(BAD_REQUEST)
                        .message(ex.getMessage())
                        .developerMessage(ExceptionUtils.getStackTrace(ex))
                        .build());
    }

}
