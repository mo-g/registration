/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vocera.cloud.coremodel.model.Admin;
import com.vocera.cloud.coremodel.model.Organization;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test cases for {@link OrganizationController}.
 *
 * @author Rohit Phatak
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrganizationControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    /**
     * Initialize mockMvc.
     */
    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * Test case for successful registration.
     *
     * @throws Exception
     */
    @Test
    @Order(1)
    public void registerOrganization() throws Exception {
        System.out.println("Executing test case for registering an organization on federation network.");

        Organization organization = new Organization();
        organization.setName("Organization");
        organization.setHealthSystemName("Sample Health System name");
        organization.setAddressLine1("Address Line 1");
        organization.setState("Maharashtra");
        organization.setCity("Mumbai");
        organization.setDomain("www.domain.com");
        organization.setLicenseKey("12345");

        Admin admin = new Admin();
        admin.setTitle("Admin Title");
        admin.setPhone("12345687890");
        admin.setName("Admin Name");
        admin.setEmail("email@a.com");
        organization.setAdmin(admin);

        Gson gson = new Gson();

        mockMvc.perform(post("/organization").content(gson.toJson(organization))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    /**
     * Failure test case for registration.
     *
     * @throws Exception
     */
    @Test
    @Order(2)
    public void registerOrganizationInvalid() throws Exception {
        System.out.println("Executing failure test case for registering an organization on federation network.");

        Organization organization = new Organization();
        organization.setName("Organization");
        organization.setHealthSystemName("Sample Health System name");
        organization.setAddressLine1("Address Line 1");
        organization.setState("Maharashtra");
        organization.setCity("Mumbai");
        organization.setDomain("www.domain.com");
        organization.setLicenseKey("12345");

        Admin admin = new Admin();
        admin.setTitle("Admin Title");
        admin.setPhone("12345687890");
        admin.setName("Admin Name");
        admin.setEmail("email@a.com");
        organization.setAdmin(admin);

        Gson gson = new Gson();

        MvcResult response = mockMvc.perform(post("/organization").content(gson.toJson(organization))
                .contentType("application/json"))
                .andExpect(status().is4xxClientError()).andReturn();

        JsonObject objResponse = gson.fromJson(response.getResponse().getContentAsString(), JsonObject.class);
        assertEquals("Error processing request", objResponse.get("message").getAsString());
        assertEquals(DataIntegrityViolationException.class, response.getResolvedException().getClass());
        System.out.println(response);
    }

    /**
     * Failure test case for registration when invalid data passed.
     *
     * @throws Exception
     */
    @Test
    @Order(3)
    public void registerOrganizationInvalidData() throws Exception {
        System.out.println("Executing failure test case while passing invalid license key for registering an " +
                "organization on federation network.");

        Organization organization = new Organization();
        organization.setName("");
        organization.setHealthSystemName("");
        organization.setAddressLine1("Address Line 1");
        organization.setState("Maharashtra");
        organization.setCity("Mumbai");
        organization.setDomain("");
        organization.setLicenseKey("");

        Admin admin = new Admin();
        admin.setTitle("Admin Title");
        admin.setPhone("12345687890");
        admin.setName("Admin Name");
        admin.setEmail("email@a.com");
        organization.setAdmin(admin);

        Gson gson = new Gson();

        MvcResult response = mockMvc.perform(post("/organization").content(gson.toJson(organization))
                .contentType("application/json"))
                .andExpect(status().is4xxClientError()).andReturn();

        JsonObject objResponse = gson.fromJson(response.getResponse().getContentAsString(), JsonObject.class);
        assertEquals("Error Raising Organization Request", objResponse.get("message").getAsString());
        System.out.println(response);
    }

    /**
     * Find organization by license key.
     *
     * @throws Exception
     */
    @Test
    @Order(4)
    public void findByLicenseKey() throws Exception {
        System.out.println("Executing test case for finding organization by license-key.");

        MvcResult response = mockMvc.perform(get("/organization/filter/license-key/12345"))
                .andExpect(status().isOk()).andReturn();
    }

    /**
     * Filter organization.
     *
     * @throws Exception
     */
    @Test
    @Order(5)
    public void filterOrganization() throws Exception {
        System.out.println("Executing test case for filtering through list of organizations.");

        StringBuilder filterURIBuilder = new StringBuilder();
        filterURIBuilder.append("/organization/filter?");
        filterURIBuilder.append("page=0");
        filterURIBuilder.append("&offset=1");

        MvcResult response = mockMvc.perform(get(filterURIBuilder.toString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
                .andReturn();
    }

    /**
     * No records found for the applied filter.
     *
     * @throws Exception
     */
    @Test
    @Order(6)
    public void invalidFilterOrganization() throws Exception {
        System.out.println("Executing test case for unavailable organization in list of organizations.");

        StringBuilder filterURIBuilder = new StringBuilder();
        filterURIBuilder.append("/organization/filter?");
        filterURIBuilder.append("page=0");
        filterURIBuilder.append("&offset=1");
        filterURIBuilder.append("&query=unavailable_organization");

        MvcResult response = mockMvc.perform(get(filterURIBuilder.toString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andReturn();
    }
}
