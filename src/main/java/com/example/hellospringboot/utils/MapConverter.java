package com.example.hellospringboot.utils;

import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Map;

@Converter(autoApply = true)
public class MapConverter<V> extends JsonConverter<Map<String, V>> {

    @Override
    protected Map<String, V> defaultEntityValue() {
        return new HashMap<>();
    }

    @Override
    protected String defaultColumnValue() {
        return "{}";
    }
}
