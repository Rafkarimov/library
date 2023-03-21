package com.nv.sberschool.library.mapper;

import com.nv.sberschool.library.dto.GenericDto;
import com.nv.sberschool.library.model.GenericModel;
import java.util.List;

public interface Mapper<E extends GenericModel, D extends GenericDto> {

    E toEntity(D dto);

    List<E> toEntities(List<D> dtos);

    D toDto(E entity);

    List<D> toDtos(List<E> entities);
}
