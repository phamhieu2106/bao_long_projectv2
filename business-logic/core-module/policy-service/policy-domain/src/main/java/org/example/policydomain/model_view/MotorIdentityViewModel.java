package org.example.policydomain.model_view;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class MotorIdentityViewModel {
    @Field(type = FieldType.Keyword)
    String identityNumber;
    @Field(type = FieldType.Keyword)
    String frameNumber;
}
