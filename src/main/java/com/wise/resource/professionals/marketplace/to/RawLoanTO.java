package com.wise.resource.professionals.marketplace.to;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class RawLoanTO {

    @NonNull
    private ResourceCollectionTO resourceCollectionTO;
    @NonNull
    private String clientName;

    @NonNull
    private int amount;

    @NonNull
    private LocalDate availabilityDate;

}
