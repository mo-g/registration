/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.service.impl;

import com.vocera.cloud.registrationservice.service.AdminService;
import com.vocera.cloud.coremodel.model.Admin;
import com.vocera.cloud.registrationservice.exception.RecordNotFoundException;
import com.vocera.cloud.registrationservice.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of @{@link AdminService}.
 *
 * @author Rohit Phatak
 */
@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    /**
     * Constructor for Autowiring @{@link AdminRepository}.
     *
     * @param adminRepository
     */
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin findByOrganizationId(Long organizationId) {
        Optional<Admin> admin = adminRepository.findByOrganizationId(organizationId);
        if (admin.isPresent()) {
            return admin.get();
        } else {
            throw new RecordNotFoundException("Invalid Organization Id, Admin not found !!");
        }
    }

    @Override
    public Admin findById(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if(admin.isPresent()){
            return admin.get();
        } else {
            throw new RecordNotFoundException("Invalid Admin Id !!");
        }
    }
}
