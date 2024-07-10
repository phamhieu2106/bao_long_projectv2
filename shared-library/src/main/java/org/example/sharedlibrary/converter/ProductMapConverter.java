package org.example.sharedlibrary.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.sharedlibrary.base_converter.BaseMapConverter;

import java.util.Map;

public class ProductMapConverter extends BaseMapConverter<Map<Object, Object>> {

    public ProductMapConverter() {
        super(new TypeReference<Map<Object, Object>>() {
        });
    }
}
