/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.repository;

import com.vocera.cloud.coremodel.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository for accessing @{@link Admin}.
 *
 * @author Rohit Phatak
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * Find Admin based on Organization Id.
     *
     * @param organizationId
     * @return
     */
    @Query("select a from Admin a where a.id=(select o.admin.id from Organization o where o.id=?1)")
    Optional<Admin> findByOrganizationId(Long organizationId);
}

