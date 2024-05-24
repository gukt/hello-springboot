package com.example.hellospringboot.utils.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.Converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Converter(autoApply = true)
public class ListConverter<E> extends JsonConverter<List<E>> {

    ListConverter() {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        typeReference = new TypeReference<>() {
            @Override
            public Type getType() {
                return new ParameterizedType() {
                    @Override
                    public Type[] getActualTypeArguments() {
                        return new Type[]{type};
                    }

                    @Override
                    public Type getRawType() {
                        return List.class;
                    }

                    @Override
                    public Type getOwnerType() {
                        return null;
                    }
                };
            }
        };
    }

    @Override
    protected List<E> defaultEntityAttributeValue() {
        return new ArrayList<>();
    }

    protected String defaultColumnValue() {
        return "[]";
    }

    public static class Int extends ListConverter<Integer> {}

    public static class Long extends ListConverter<java.lang.Long> {}
}
