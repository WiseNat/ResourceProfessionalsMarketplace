package com.wise.resource.professionals.marketplace.to;

import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import lombok.Data;
import lombok.NonNull;

@Data
public class RawResourceTO {

    @NonNull
    private ResourceEntity resourceEntity;

    @NonNull
    private String mainRole;

    @NonNull
    private String subRole;

    @NonNull
    private String banding;

    @NonNull
    private String costPerHour;

}
