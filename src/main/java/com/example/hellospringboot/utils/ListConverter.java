package com.example.hellospringboot.utils;

import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;

@Converter(autoApply = true)
public class ListConverter<E> extends JsonConverter<List<E>> {

    @Override
    protected List<E> defaultEntityValue() {
        return new ArrayList<>();
    }

    protected String defaultColumnValue() {
        return "[]";
    }

    public static class Int extends ListConverter<Integer> {}

    public static class Long extends ListConverter<Long> {}
}
