package ru.Liga.waiter.mapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Function;

public class GenericMapper<E, D> {

    private final Function<E, D> toDtoFunction;
    private final Function<D, E> toEntityFunction;

    public GenericMapper(Function<E, D> toDtoFunction, Function<D, E> toEntityFunction) {
        this.toDtoFunction = toDtoFunction;
        this.toEntityFunction = toEntityFunction;
    }

    public D toDto(E entity) {
        return entity == null ? null : toDtoFunction.apply(entity);
    }

    public E toEntity(D dto) {
        return dto == null ? null : toEntityFunction.apply(dto);
    }

    public List<D> toDtoList(List<E> entities) {
        return entities == null ? null : entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<E> toEntityList(List<D> dtos) {
        return dtos == null ? null : dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
