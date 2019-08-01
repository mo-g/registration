/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.service;

import com.vocera.cloud.coremodel.model.Organization;

/**
 * Organization Service.
 *
 * @author Rohit Phatak
 */
public interface OrganizationService {

    /**
     * Find Organization by licenseKey.
     *
     * @param licenseKey
     * @return
     */
    Organization findByLicenseKey(String licenseKey);

    /**
     * Find Organization by Organization Id.
     *
     * @param id
     * @return
     */
    Organization findById(Long id);

    /**
     * Save an Organization.
     *
     * @param organization
     * @return
     */
    Organization register(Organization organization);
}
