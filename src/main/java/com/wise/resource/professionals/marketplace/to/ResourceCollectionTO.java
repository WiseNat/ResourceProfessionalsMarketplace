package com.wise.resource.professionals.marketplace.to;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * A POJO for a collection of similar resources
 *
 * @see com.wise.resource.professionals.marketplace.repository.ResourceRepository.IResourceCollection
 * @see com.wise.resource.professionals.marketplace.service.LoanService#iResourceCollectionToResourceCollectionTO
 * @see com.wise.resource.professionals.marketplace.service.LoanService#getLoanables
 */
@Data
public class ResourceCollectionTO {

    @NotNull
    private ResourceTO resource;

    @NotNull
    private long quantity;

}
