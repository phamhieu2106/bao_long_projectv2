package org.example.policydomain.view;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.policydomain.model_view.HealthIdentityVIewModel;
import org.example.policydomain.model_view.MotorIdentityViewModel;
import org.example.sharedlibrary.enumeration.ProductType;
import org.example.sharedlibrary.enumeration.QuotationStatus;
import org.example.sharedlibrary.enumeration.QuotationTypeStatus;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@Document(indexName = "quotations")
@NoArgsConstructor
@Builder
public class PolicyView {

    @Field(type = FieldType.Keyword)
    String id;
    @Field(type = FieldType.Keyword)
    String quotationCode;
    @Field(type = FieldType.Keyword)
    String policyCode;
    @Field(type = FieldType.Keyword)
    QuotationStatus quotationStatus;
    @Field(type = FieldType.Keyword)
    QuotationTypeStatus quotationTypeStatus;
    @Field(type = FieldType.Keyword)
    String productCode;
    @Field(type = FieldType.Keyword)
    String createdBy;
    @Field(type = FieldType.Keyword)
    String userOffice;
    @Field(type = FieldType.Keyword)
    String apartment;
    @Field(type = FieldType.Keyword)
    ProductType productType;
    @Field(type = FieldType.Keyword)
    String customerNameKeyword;

    @Field(type = FieldType.Text)
    String customerName;

    @Field(type = FieldType.Nested)
    List<MotorIdentityViewModel> motorIdentityViewModel;

    @Field(type = FieldType.Nested)
    List<HealthIdentityVIewModel> healthIdentityVIewModel;

    @Field(type = FieldType.Double)
    Double feeAfterTax;

    @Field(type = FieldType.Date)
    Date createdAt;
    @Field(type = FieldType.Date)
    Date approvedAt;
}
