package com.example.hellospringboot.utils;

import jakarta.persistence.Converter;

import java.util.HashSet;
import java.util.Set;

@Converter(autoApply = true)
public class SetConverter<E> extends JsonConverter<Set<E>> {

    @Override
    protected Set<E> defaultEntityValue() {
        return new HashSet<>();
    }

    @Override
    protected String defaultColumnValue() {
        return "[]";
    }
}
