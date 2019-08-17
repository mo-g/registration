/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.service;

import com.vocera.cloud.coremodel.model.Organization;
import com.vocera.cloud.coremodel.model.PageResponse;
import org.springframework.data.domain.Sort;

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

    /**
     * Service for filtering and paginating of an organization.
     *
     * @param page
     * @param offset
     * @param query
     * @param sort
     * @param order
     * @return
     */
    PageResponse<Organization> filterOrganization(int page, int offset, String query, String sort,
                                                  Sort.Direction order);
}
