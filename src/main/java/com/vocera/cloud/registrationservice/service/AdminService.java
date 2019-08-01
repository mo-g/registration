/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.service;

import com.vocera.cloud.coremodel.model.Admin;

/**
 * Admin Service.
 *
 * @author Rohit Phatak
 */
public interface AdminService {

    /**
     * Service for finding Admin for Organization Id.
     *
     * @param organizationId
     * @return
     */
    Admin findByOrganizationId(Long organizationId);

    /**
     * Find the Admin by Admin Id.
     *
     * @param id
     * @return
     */
    Admin findById(Long id);
}
