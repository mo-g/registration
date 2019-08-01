/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.service.impl;

import com.vocera.cloud.coremodel.model.Admin;
import com.vocera.cloud.coremodel.model.Organization;
import com.vocera.cloud.registrationservice.service.AdminService;
import com.vocera.cloud.registrationservice.service.OrganizationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test cases for Admin Service.
 *
 * @author Rohit Phatak
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminServiceImplTest {

    private static final Logger LOGGER = Logger.getLogger(AdminServiceImplTest.class.getName());

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private AdminService adminService;

    @BeforeAll
    public void init(){
        LOGGER.info("Init for Admin service");

        Organization organization = new Organization();
        organization.setName("Organization1");
        organization.setHealthSystemName("Health System 2");
        organization.setAddressLine1("Address Line 1");
        organization.setState("Maharashtra");
        organization.setCity("Mumbai");
        organization.setDomain("www.domain.com");
        organization.setLicenseKey("123456");

        Admin admin = new Admin();
        admin.setTitle("Admin Title");
        admin.setPhone("12345687890");
        admin.setName("Admin Name");
        admin.setEmail("email@a.com");
        organization.setAdmin(admin);

        Organization organization1 = organizationService.register(organization);
        LOGGER.info("Org Id : "+organization1.getId());
    }

    @Test
    public void findByOrganizationId() {
        LOGGER.info("Executing success test case for Finding an admin by organizationId.");

        assertNotNull(adminService.findByOrganizationId(4l));
    }

    @Test
    public void findById() {
        LOGGER.info("Executing success test case for Finding Admin by id.");

        assertNotNull(adminService.findById(4l));
    }
}