package org.example.sharedlibrary.base_request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_constant.PageConstant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseRequest {
    int pageNumber = PageConstant.PAGE_NUMBER;
    int pageSize = PageConstant.PAGE_SIZE;
    String sortOrder = PageConstant.PAGE_SORT_TYPE;
    String keyword = PageConstant.PAGE_DEFAULT_VALUE;
    String[] sortBys = {PageConstant.PAGE_DEFAULT_SORT_BYS};
}
