/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.service.impl;

import com.vocera.cloud.coremodel.model.Admin;
import com.vocera.cloud.coremodel.model.Organization;
import com.vocera.cloud.coremodel.model.PageResponse;
import com.vocera.cloud.registrationservice.exception.RecordNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for Organization Service.
 *
 * @author Rohit Phatak
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class OrganizationServiceImplTest {

    @Autowired
    private OrganizationServiceImpl organizationService;

    /**
     * Initalize Test cases.
     */
    @BeforeAll
    public void init() {
        System.out.println("Initializing test cases with an Organization.");
        Organization organization = new Organization();
        organization.setName("Organization1");
        organization.setHealthSystemName("Health System 1");
        organization.setAddressLine1("Address Line 1");
        organization.setState("Maharashtra");
        organization.setCity("Mumbai");
        organization.setDomain("www.domain.com");
        organization.setLicenseKey("1234");

        Admin admin = new Admin();
        admin.setTitle("Admin Title");
        admin.setPhone("12345687890");
        admin.setName("Admin Name");
        admin.setEmail("email@a.com");
        organization.setAdmin(admin);

        organizationService.register(organization);
    }

    /**
     * Test case for successful registration.
     */
    @Order(1)
    @Test
    public void successfulRegistration() {
        System.out.println("Executing test case for Successful Registration");

        Organization organization = new Organization();
        organization.setName("Organization2");
        organization.setHealthSystemName("Health System 1");
        organization.setAddressLine1("Address Line 1");
        organization.setState("Maharashtra");
        organization.setCity("Mumbai");
        organization.setDomain("www.domain.com");
        organization.setLicenseKey("abcd");

        Admin admin = new Admin();
        admin.setTitle("Admin Title");
        admin.setPhone("12345687890");
        admin.setName("Admin Name");
        admin.setEmail("email@a.com");
        organization.setAdmin(admin);

        assertNotNull(organizationService.register(organization));
    }

    /**
     * Test case for Constraint Violation.
     */
    @Order(2)
    @Test
    public void duplicateNameAndHealthSystemName() {
        System.out.println("Executing failure test case for Duplicate Organization and Health System Name combination" +
                " while Registration");

        Organization organization = new Organization();
        organization.setName("Organization2");
        organization.setHealthSystemName("Health System 1");
        organization.setAddressLine1("Address Line 1");
        organization.setState("Maharashtra");
        organization.setCity("Mumbai");
        organization.setDomain("www.domain.com");
        organization.setLicenseKey("abcd");

        Admin admin = new Admin();
        admin.setTitle("Admin Title");
        admin.setPhone("12345687890");
        admin.setName("Admin Name");
        admin.setEmail("email@a.com");
        organization.setAdmin(admin);

        assertThrows(DataIntegrityViolationException.class, () -> {
            organizationService.register(organization);
        });
    }

    /**
     * Success test case for checking if License key is valid.
     */
    @Order(3)
    @Test
    public void checkValidLicenseKey() {
        System.out.println("Executing Test for Valid License Key");
        Organization organization = organizationService.findByLicenseKey("1234");
        assertTrue(organization != null);
    }

    /**
     * Failure test case for checking if License key is valid.
     */
    @Order(4)
    @Test
    public void checkInvalidLicenseKey() {
        System.out.println("Executing Test for Invalid License Key");
        assertThrows(RecordNotFoundException.class, () -> {
            organizationService.findByLicenseKey("4321");
        });
    }

    /**
     * Success test case for finding Organization by Id.
     */
    @Order(5)
    @Test
    public void validFindById() {
        System.out.println("Executing Test for Valid findById");
        assertNotNull(organizationService.findById(1l));
    }

    /**
     * Failure test case for finding Organization by Id.
     */
    @Order(6)
    @Test
    public void invalidFindById() {
        System.out.println("Executing Test for Invalid findById");
        assertThrows(RecordNotFoundException.class, () -> {
            organizationService.findById(10l);
        });
    }

    @Test
    public void filterOrganization() {
        System.out.println("Executing test case for default pagination through organization.");
        PageResponse<Organization> response = organizationService.filterOrganization(0, 1, "",
                "name", Sort.Direction.ASC);
        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.getData().size() > 0);
    }

    @Test
    public void searchOrganizationByName() {
        System.out.println("Executing test case for searching Organization Name through organization.");
        String organizationName = "Organization1";
        PageResponse<Organization> response = organizationService.filterOrganization(0, 1, organizationName,
                "name", Sort.Direction.ASC);
        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.getData().size() > 0);
        assertEquals(organizationName, response.getData().get(0).getName());
    }

    @Test
    public void searchOrganizationByHealthSystemName() {
        System.out.println("Executing test case for searching Organization Name through organization.");
        String healthSystemName = "Health System 1";
        PageResponse<Organization> response = organizationService.filterOrganization(0, 1,
                healthSystemName, "name", Sort.Direction.ASC);
        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.getData().size() > 0);
        assertEquals(healthSystemName, response.getData().get(0).getHealthSystemName());
    }

}
