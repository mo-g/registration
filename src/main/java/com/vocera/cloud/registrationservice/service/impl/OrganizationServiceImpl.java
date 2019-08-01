/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.service.impl;

import com.vocera.cloud.registrationservice.service.OrganizationService;
import com.vocera.cloud.coremodel.model.Organization;
import com.vocera.cloud.registrationservice.exception.RecordNotFoundException;
import com.vocera.cloud.registrationservice.repository.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation for @{@link OrganizationService}.
 *
 * @author Rohit Phatak
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private OrganizationRepository organizationRepository;

    /**
     * Constructor for autowiring @{@link OrganizationRepository}.
     *
     * @param organizationRepository
     */
    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    /**
     *
     * @param licenseKey
     * @return
     */
    @Override
    public Organization findByLicenseKey(String licenseKey) {

        LOGGER.info("Find By License Key Called for {}",licenseKey);

        Optional<Organization> organization = organizationRepository.findByLicenseKey(licenseKey);
        if (organization.isPresent()){
            return organization.get();
        } else {
            throw new RecordNotFoundException("Invalid Licence Key : "+licenseKey);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Organization findById(Long id) {
        Optional<Organization> organization = organizationRepository.findById(id);
        if (organization.isPresent()){
            return organization.get();
        } else {
            throw new RecordNotFoundException("Invalid Organization id : "+id);
        }
    }

    /**
     *
     * @param organization
     * @return
     */
    @Override
    public Organization register(Organization organization) {
        // TODO Check for validations
        return organizationRepository.save(organization);
    }
}
