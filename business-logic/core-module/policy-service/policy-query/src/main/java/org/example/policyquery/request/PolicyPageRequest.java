package org.example.policyquery.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_request.BaseRequest;
import org.example.sharedlibrary.enumeration.ProductType;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PolicyPageRequest extends BaseRequest {
    String userOffice;
    String apartment;
    ProductType productType;
    Date createdFrom;
    Date createdTo;
    Date approvedAt;
}
