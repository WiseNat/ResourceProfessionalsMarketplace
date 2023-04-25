package com.wise.ResourceProfessionalsMarketplace.util;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class PersistUtil {
    public <A, S extends T, T, ID> S persistTo(A TO, S entity, JpaRepository<T, ID> repository) {
        BeanUtils.copyProperties(TO, entity);
        repository.save(entity);

        return entity;
    }
}
