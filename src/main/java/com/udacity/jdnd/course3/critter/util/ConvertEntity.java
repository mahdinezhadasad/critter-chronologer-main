package com.udacity.jdnd.course3.critter.util;

import org.springframework.beans.BeanUtils;

/**
 * Where T is entity and K is dto
 */
public class ConvertEntity<T, K>{

    public K toDto(T entity, K dto) {
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public T toEntity(T entity, K dto) {
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
