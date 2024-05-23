package com.example.hellospringboot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class JsonConverter<T> implements AttributeConverter<T, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TypeReference<T> typeReference = new TypeReference<>() {};

    @Override
    public String convertToDatabaseColumn(T attribute) {
        if (attribute == null) {
            return defaultColumnValue();
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting object to JSON", e);
        }
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty() || "null".equalsIgnoreCase(dbData)) {
            return defaultEntityValue();
        }
        try {
            return objectMapper.readValue(dbData, typeReference);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON to object", e);
        }
    }

    protected T defaultEntityValue() {
        return null;
    }

    protected String defaultColumnValue() {
        return null;
    }
}