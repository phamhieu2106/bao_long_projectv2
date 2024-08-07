package org.example.quotationdomain.domain.view;

import lombok.*;
import org.example.quotationdomain.domain.model_view.HealthIdentityVIewModel;
import org.example.quotationdomain.domain.model_view.MotorIdentityViewModel;
import org.example.sharedlibrary.enumeration.ProductType;
import org.example.sharedlibrary.enumeration.QuotationStatus;
import org.example.sharedlibrary.enumeration.QuotationTypeStatus;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Document(indexName = "quotations")
@NoArgsConstructor
@Builder
public class QuotationView {

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
