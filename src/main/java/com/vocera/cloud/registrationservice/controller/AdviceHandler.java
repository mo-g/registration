/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.controller;

import com.vocera.cloud.coremodel.model.ErrorResponse;
import com.vocera.cloud.registrationservice.exception.InvalidOrganizationException;
import com.vocera.cloud.registrationservice.exception.RecordNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Advice Handler for raised exceptions and errors.
 *
 * @author Rohit Phatak
 */
@ControllerAdvice
public class AdviceHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdviceHandler.class);

    /**
     * Global exception handler.
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Server Error", details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Exception Handler for @{@link RecordNotFoundException}
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<ErrorResponse> entityNotFound(RecordNotFoundException ex) {
        LOGGER.info("Entity Not Found !!!!!");
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Record Not Found", details);
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    /**
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        LOGGER.info("handleMethodArgumentNotValid : Called");
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse("Validation Failed", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler for @{@link ConstraintViolationException}
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        LOGGER.info("ConstraintViolationException Called");
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ErrorResponse apiError =
                new ErrorResponse(ex.getLocalizedMessage(), errors);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler for {@link DataIntegrityViolationException}
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        LOGGER.info("DataIntegrityViolationException Called");

        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException){
            org.hibernate.exception.ConstraintViolationException constraintViolationException = (org.hibernate.exception.ConstraintViolationException) ex.getCause();
            if ("UK_name_health_system_name".equalsIgnoreCase(constraintViolationException.getConstraintName())){
                errors.add("Invalid Health System Name or Organization Name");
            } else if ("UK_license_key".equalsIgnoreCase(constraintViolationException.getConstraintName())){
                errors.add("Invalid License Key");
            } else {
                errors.add(constraintViolationException.getConstraintName());
            }
        } else {
            errors.add(ex.getCause().getMessage());
        }
        ErrorResponse apiError =
                new ErrorResponse("Error processing request", errors);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(InvalidOrganizationException.class)
    public final ResponseEntity<ErrorResponse> entityNotFound(InvalidOrganizationException ex) {
        LOGGER.info("Cannot Process Organization Request !!!!!");
        List<String> details = new ArrayList<>();
        for (Object error : ex.getErrors()){
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                details.add(fieldError.getDefaultMessage());
            }

        }
        ErrorResponse error = new ErrorResponse("Error Raising Organization Request", details);
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
}
