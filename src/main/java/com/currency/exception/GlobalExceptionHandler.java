package com.currency.exception;


import com.currency.dto.ErrorResponse;
import com.currency.exception.custom.CurrencyException;
import com.currency.exception.custom.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAlreadyUserExists(UserAlreadyExistsException userAlreadyExistsException, HttpServletRequest httpServletRequest){
        ErrorResponse response = ErrorResponse.builder()
                .error("User Already Exists")
                .path(httpServletRequest.getRequestURI())
                .localDateTime(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .message(userAlreadyExistsException.getMessage()).build();

        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleCustomCurrencyException(CurrencyException currencyException , HttpServletRequest httpServletRequest){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Conflict")
                .path(httpServletRequest.getRequestURI())
                .localDateTime(LocalDateTime.now())
                .status(HttpStatus.CONTINUE.value())
                .message(currencyException.getMessage()).build();

        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGenricCurrencyException(Exception e,HttpServletRequest httpServletRequest){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Conflict")
                .path(httpServletRequest.getRequestURI())
                .localDateTime(LocalDateTime.now())
                .status(HttpStatus.CONTINUE.value())
                .message(e.getMessage()).build();

        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

}
