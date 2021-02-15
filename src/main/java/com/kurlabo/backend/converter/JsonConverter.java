package com.kurlabo.backend.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class JsonConverter implements AttributeConverter<Object, String> {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(Object meta) {
        return objectMapper.writeValueAsString(meta);
    }

    @SneakyThrows
    @Override
    public Object convertToEntityAttribute(String dbData) {
        return objectMapper.readValue(dbData, Object.class);
    }
}
