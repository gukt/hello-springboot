package com.example.hellospringboot.utils.converter;

import jakarta.persistence.Converter;

import java.util.HashSet;
import java.util.Set;

@Converter(autoApply = true)
public class SetConverter<E> extends JsonConverter<Set<E>> {

    @Override
    protected Set<E> defaultEntityAttributeValue() {
        return new HashSet<>();
    }

    @Override
    protected String defaultColumnValue() {
        return "[]";
    }

    public static class Int extends SetConverter<Integer> {}

    public static class Long extends SetConverter<Long> {}
}
