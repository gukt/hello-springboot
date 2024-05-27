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
        typeReference = new TypeReference<>() {};

        Type elementType = getClass().equals(ListConverter.class)
                ? Object.class
                : ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        typeReference = new TypeReference<>() {
            @Override
            public Type getType() {
                return new ParameterizedType() {
                    @Override
                    public Type[] getActualTypeArguments() {
                        return new Type[]{elementType};
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

    public static class Int extends ListConverter<Integer> {
        Int() {
            // 原来这么写是最简单的
            // 在 ListConverter 里写，就只能获取到 List<E> 的类型，而无法获取到 E 的真实类型，
            // 在这里就不会了，可以获得到完整的 List<Integer> 类型。
            typeReference = new TypeReference<>() {};
        }
    }

    public static class Long extends ListConverter<java.lang.Long> {}
}
