package com.esantefutur.esantefutur.service.mappers;

public interface EntityMapper<D, E> {
    D fromEntity(E entity);
    E toEntity(D dto);
}
