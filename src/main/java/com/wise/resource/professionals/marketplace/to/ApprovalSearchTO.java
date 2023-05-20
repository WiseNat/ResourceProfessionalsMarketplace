package com.wise.resource.professionals.marketplace.to;

import lombok.Data;
import lombok.NonNull;

/**
 * A POJO for an Approval Search
 *
 * @see com.wise.resource.professionals.marketplace.controller.AdminController#populatePredicateApprovals
 * @see com.wise.resource.professionals.marketplace.service.AdminService#getApprovals
 */
@Data
public class ApprovalSearchTO {

    @NonNull
    private boolean isResourceAllowed;

    @NonNull
    private boolean isProjectManagerAllowed;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String email;
}
