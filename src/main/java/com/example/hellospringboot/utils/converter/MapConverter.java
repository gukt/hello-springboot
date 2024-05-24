package com.example.hellospringboot.utils.converter;

import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Map;

@Converter(autoApply = true)
public class MapConverter<V> extends JsonConverter<Map<String, V>> {

    @Override
    protected Map<String, V> defaultEntityAttributeValue() {
        return new HashMap<>();
    }

    @Override
    protected String defaultColumnValue() {
        return "{}";
    }

    public static class Int extends MapConverter<Integer> {}

    public static class Long extends MapConverter<Long> {}
}
