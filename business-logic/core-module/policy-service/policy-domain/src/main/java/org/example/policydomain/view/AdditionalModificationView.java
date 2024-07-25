package org.example.policydomain.view;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_constant.view.AdditionalModificationViewConstant;
import org.example.sharedlibrary.enumeration.additional_modification.AdditionalModificationStatus;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationType;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationTypeName;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@Document(indexName = AdditionalModificationViewConstant.ADDITIONAL_MODIFICATION_INDEX)
@NoArgsConstructor
@Builder
public class AdditionalModificationView {
    @Id
    @Field(type = FieldType.Keyword)
    String id;
    @Field(type = FieldType.Keyword)
    String additionalModificationCode;
    @Field(type = FieldType.Keyword)
    AdditionalModificationStatus additionalModificationStatus;
    @Field(type = FieldType.Keyword)
    ModificationType modificationType;
    @Field(type = FieldType.Keyword)
    ModificationTypeName modificationTypeName;
    @Field(type = FieldType.Date)
    Date createdAt;
    @Field(type = FieldType.Date)
    Date effectiveDate;
    @Field(type = FieldType.Keyword)
    String approvedBy;
}
