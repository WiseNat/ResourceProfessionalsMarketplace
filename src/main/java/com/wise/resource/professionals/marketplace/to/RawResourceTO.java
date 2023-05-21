package com.wise.resource.professionals.marketplace.to;

import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import lombok.Data;
import lombok.NonNull;

/**
 * A POJO for the raw user inputs when updating the details of a resource
 *
 * @see ResourceTO
 * @see com.wise.resource.professionals.marketplace.controller.ResourceController#saveDetailsClicked
 * @see com.wise.resource.professionals.marketplace.service.ResourceService#createResourceTo
 */
@Data
public class RawResourceTO {

    @NonNull
    private ResourceEntity resourceEntity;

    @NonNull
    private String mainRole;

    private String subRole;

    @NonNull
    private String banding;

    @NonNull
    private String costPerHour;

}
