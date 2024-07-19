package org.example.sharedlibrary.base_request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SortRequest {
    String fieldName;
    boolean sortAscending = false;

    public SortRequest(String fieldName) {
        this.fieldName = fieldName;
    }

}
