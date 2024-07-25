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
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@Document(indexName = AdditionalModificationViewConstant.ADDITIONAL_MODIFICATION_HISTORY_INDEX)
@NoArgsConstructor
@Builder
public class AdditionalModificationHistoryView {
    @Id
    @Field(type = FieldType.Keyword)
    String id;
    @Field(type = FieldType.Keyword)
    String aggregateId;
    @Field(type = FieldType.Keyword)
    String aggregateType;
    @Field(type = FieldType.Keyword)
    String createdBy;
    @Field(type = FieldType.Date)
    Date createdAt;
    @Field(type = FieldType.Date)
    Date timeStamp;
}
