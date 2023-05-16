package com.wise.resource.professionals.marketplace.to;

import lombok.Data;
import lombok.NonNull;

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
