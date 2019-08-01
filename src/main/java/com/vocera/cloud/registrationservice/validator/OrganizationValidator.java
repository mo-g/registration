/*
 * Copyright (c) Vocera Communications, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * Vocera Communications, Inc.
 */

package com.vocera.cloud.registrationservice.validator;

import com.vocera.cloud.coremodel.model.Organization;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for Organization.
 *
 * @author Rohit Phatak
 */
@Component
public class OrganizationValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Organization.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof Organization) {
            Organization organization = (Organization) target;

            if (StringUtils.isEmpty(organization.getDomain())) {
                errors.rejectValue("domain","invalid.domain","Invalid Domain");
            }
            if (StringUtils.isEmpty(organization.getHealthSystemName())) {
                errors.rejectValue("healthSystemName","invalid.healthSystemName","Invalid Health System Name");
            }
            if (StringUtils.isEmpty(organization.getLicenseKey())) {
                errors.rejectValue("licenseKey","invalid.licensekey","Invalid License Key");
            }
            if (StringUtils.isEmpty(organization.getName())) {
                errors.rejectValue("name","invalid.name","Invalid Organization Name");
            }
        }
    }
}
