package org.example.customerdomain.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.customerdomain.enumeration.StatusCustomer;
import org.example.sharedlibrary.base_constant.ViewConstant;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = ViewConstant.CUSTOMER_INDEX)
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerView {
    @Id
    @Field(type = FieldType.Keyword)
    String id;
    @Field(type = FieldType.Keyword)
    String customerCode;
    @Field(type = FieldType.Keyword)
    StatusCustomer statusCustomer;
    @Field(type = FieldType.Text)
    String customerName;
    @Field(type = FieldType.Keyword)
    String customerNameKeyword;
    @Field(type = FieldType.Keyword)
    String email;
    @Field(type = FieldType.Keyword)
    String phoneNumber;
    @Field(type = FieldType.Keyword)
    String inChargeBy;
}
