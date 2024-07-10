package org.example.customerdomain.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.sharedlibrary.base_converter.BaseMapConverter;

import java.util.Map;

public class InformationMapConverter extends BaseMapConverter<Map<Object, Object>> {
    public InformationMapConverter() {
        super(new TypeReference<Map<Object, Object>>() {
        });
    }
}
