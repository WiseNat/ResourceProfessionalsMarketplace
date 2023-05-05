package com.wise.resource.professionals.marketplace.to;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResourceCollectionTO {

    @NotNull
    private ResourceTO resource;

    @NotNull
    private long quantity;

}
