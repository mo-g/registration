/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.controller;

import com.vocera.cloud.registrationservice.service.AdminService;
import com.vocera.cloud.coremodel.model.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Endpoint for Admin Services.
 *
 * @author Rohit Phatak
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    private AdminService adminService;

    /**
     * Constructor for Autowiring @{@link AdminService}.
     *
     * @param adminService
     */
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Filter Admin by Organization Id.
     *
     * @param organizationId
     * @return
     */
    @GetMapping("/filter/organizationId/{organizationId}")
    public ResponseEntity<Admin> findByOrganizationId(@PathVariable("organizationId") Long organizationId){
        LOGGER.info("Find By OrganizationId Called for OrganizationId : {}",organizationId);
        return new ResponseEntity<>(adminService.findByOrganizationId(organizationId), HttpStatus.OK);
    }

    @GetMapping("/filter/id/{id}")
    public ResponseEntity<Admin> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(adminService.findById(id), HttpStatus.OK);
    }
}
