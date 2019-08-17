/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.service.impl;

import com.vocera.cloud.coremodel.model.Organization;
import com.vocera.cloud.coremodel.model.PageResponse;
import com.vocera.cloud.registrationservice.exception.RecordNotFoundException;
import com.vocera.cloud.registrationservice.repository.OrganizationRepository;
import com.vocera.cloud.registrationservice.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
     * @param licenseKey
     * @return
     */
    @Override
    public Organization findByLicenseKey(String licenseKey) {

        LOGGER.info("Find By License Key Called for {}", licenseKey);

        Optional<Organization> organization = organizationRepository.findByLicenseKey(licenseKey);
        if (organization.isPresent()) {
            return organization.get();
        } else {
            throw new RecordNotFoundException("Invalid Licence Key : " + licenseKey);
        }
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Organization findById(Long id) {
        Optional<Organization> organization = organizationRepository.findById(id);
        if (organization.isPresent()) {
            return organization.get();
        } else {
            throw new RecordNotFoundException("Invalid Organization id : " + id);
        }
    }

    /**
     * @param organization
     * @return
     */
    @Override
    public Organization register(Organization organization) {
        return organizationRepository.save(organization);
    }

    /**
     * @param page
     * @param offset
     * @param query
     * @param sort
     * @param order
     * @return
     */
    @Override
    public PageResponse<Organization> filterOrganization(int page, int offset, String query, String sort,
                                                         Sort.Direction order) {
        LOGGER.info("Filter Organization called for page:{}, offset:{}, query:{}, sort:{}, order:{}", page, offset,
                query, sort, order);
        Page<Organization> queryResponse = organizationRepository.findByNameContainsOrHealthSystemNameContains(query,
                query, PageRequest.of(page, offset, Sort.by(order, sort)));
        if (queryResponse != null) {
            return new PageResponse<>(queryResponse.getContent(), page, offset, queryResponse.getTotalElements());
        } else {
            throw new RecordNotFoundException("Error processing page request.");
        }
    }
}
