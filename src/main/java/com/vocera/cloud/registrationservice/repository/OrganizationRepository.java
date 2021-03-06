/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.repository;

import com.vocera.cloud.coremodel.model.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for accessing @{@link Organization}.
 *
 * @author Rohit Phatak
 */
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    /**
     * Find Organization based on License Key.
     *
     * @param licenseKey
     * @return
     */
    Optional<Organization> findByLicenseKey(String licenseKey);

    /**
     * Filter @{@link Organization} by Organization name and Health System Name.
     * Pageable used for Pagination and Sorting.
     *
     * @param name
     * @param healthSystemName
     * @param pageable
     * @return
     */
    Page<Organization> findByNameContainsOrHealthSystemNameContains(String name, String healthSystemName,
                                                                    Pageable pageable);
}
