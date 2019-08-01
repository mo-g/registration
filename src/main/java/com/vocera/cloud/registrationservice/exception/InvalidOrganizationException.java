/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.exception;

import java.util.List;

/**
 * Custom exception in case of an invalid request while registering an Organization.
 *
 * @author Rohit Phatak
 */
public class InvalidOrganizationException extends RuntimeException {

    private List<Object> errors;

    /**
     * Constructor
     *
     * @param message
     * @param errors
     */
    public InvalidOrganizationException(String message, List errors) {
        super(message);
        this.errors = errors;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }
}
