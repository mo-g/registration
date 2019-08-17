/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test cases for @{@link AdminController}
 *
 * @author Rohit Phatak
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    /**
     * Initialize mockMvc
     */
    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * Filter admin by organization id.
     *
     * @throws Exception
     */
    @Test
    public void filterByOrganizationId() throws Exception {
        System.out.println("Executing test case for filtering by organizationId");

        mockMvc.perform(get("/admin/filter/organizationId/1"))
                .andExpect(status().isOk());
    }

    /**
     * No admin found for the particular organization id.
     *
     * @throws Exception
     */
    @Test
    public void invalidFilterByOrganizationId() throws Exception {
        System.out.println("Executing failure test case for filtering by organizationId");

        mockMvc.perform(get("/admin/filter/organizationId/100"))
                .andExpect(status().is4xxClientError());
    }

    /**
     * Find admin by admin id.
     *
     * @throws Exception
     */
    @Test
    public void filterByAdminId() throws Exception {
        System.out.println("Executing test case for filtering by adminId");

        mockMvc.perform(get("/admin/filter/id/1"))
                .andExpect(status().isOk());
    }

    /**
     * Admin not present for the admin id passed in request.
     *
     * @throws Exception
     */
    @Test
    public void invalidFilterByAdminId() throws Exception {
        System.out.println("Executing failure test case for filtering by adminId");

        mockMvc.perform(get("/admin/filter/id/100"))
                .andExpect(status().is4xxClientError());
    }
}
