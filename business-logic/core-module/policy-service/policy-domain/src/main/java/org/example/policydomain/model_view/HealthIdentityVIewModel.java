package org.example.policydomain.model_view;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class HealthIdentityVIewModel {
    @Field(type = FieldType.Keyword)
    String customerName;
    @Field(type = FieldType.Keyword)
    String identityNumber;
}
