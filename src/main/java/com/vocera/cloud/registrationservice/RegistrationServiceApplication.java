/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice;

import com.vocera.cloud.coremodel.model.Admin;
import com.vocera.cloud.registrationservice.repository.AdminRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring Boot Entry Point for Registration Service.
 *
 * @author Rohit Phatak
 */
@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {AdminRepository.class})
@EntityScan(basePackageClasses = {Admin.class})
public class RegistrationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistrationServiceApplication.class, args);
    }

}