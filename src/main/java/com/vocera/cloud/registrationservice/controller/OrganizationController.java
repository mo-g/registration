/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.controller;

import com.vocera.cloud.coremodel.model.Organization;
import com.vocera.cloud.coremodel.model.PageResponse;
import com.vocera.cloud.registrationservice.exception.InvalidOrganizationException;
import com.vocera.cloud.registrationservice.service.OrganizationService;
import com.vocera.cloud.registrationservice.validator.OrganizationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST Endpoint for Organization Services.
 *
 * @author Rohit Phatak
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);

    private OrganizationService organizationService;

    private OrganizationValidator organizationValidator;

    /**
     * @param organizationService
     * @param organizationValidator
     */
    public OrganizationController(OrganizationService organizationService,
                                  OrganizationValidator organizationValidator) {
        this.organizationService = organizationService;
        this.organizationValidator = organizationValidator;
    }

    @InitBinder("organization")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(organizationValidator);
    }

    /**
     * Filter Organization based on License Key.
     *
     * @param licenseKey
     * @return
     */
    @GetMapping("/filter/license-key/{licenseKey}")
    public ResponseEntity<Organization> findByLicenseKey(@PathVariable("licenseKey") String licenseKey) {
        LOGGER.info("Find by License Key called for : {}", licenseKey);
        Organization organization = organizationService.findByLicenseKey(licenseKey);
        return new ResponseEntity<>(organization, HttpStatus.OK);
    }

    /**
     * Register an Organization on Federation Network.
     *
     * @param organization
     * @return
     */
    @PostMapping("")
    public ResponseEntity<Organization> register(@Valid @RequestBody Organization organization,
                                                 BindingResult bindingResult) {

        LOGGER.info("Register onto Federation Network called.");

        if (bindingResult.hasErrors()) {
            throw new InvalidOrganizationException("Invalid Request for Organization", bindingResult.getAllErrors());
        } else {
            organization = organizationService.register(organization);
        }
        return new ResponseEntity<>(organization, HttpStatus.OK);
    }

    /**
     * This API is used for filtering and paginating of Organizations.
     *
     * @param page
     * @param offset
     * @param query
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("/filter")
    public ResponseEntity<PageResponse<Organization>> filterOrganization(
            @RequestParam("page") int page,
            @RequestParam("offset") int offset,
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(value = "order", required = false, defaultValue = "ASC") Sort.Direction order) {
        return new ResponseEntity<>(organizationService.filterOrganization(page, offset, query, sort, order),
                HttpStatus.OK);
    }
}
