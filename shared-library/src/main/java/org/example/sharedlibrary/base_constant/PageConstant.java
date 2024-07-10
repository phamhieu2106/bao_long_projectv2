package org.example.sharedlibrary.base_constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PageConstant {

    public static final int PAGE_NUMBER = 0;
    public static final int PAGE_SIZE = 10;
    public static final String PAGE_SORT_TYPE = "ASC";
    public static final String PAGE_DEFAULT_VALUE = "";
    public static final String PAGE_DEFAULT_SORT_BYS = "id";

    private PageConstant() {
        super();
    }

    public static Sort getSortBys(String[] sortBys, String sortOrder) {
        Sort sort;
        if ("ASC".equals(sortOrder)) {
            sort = Sort.by(Sort.Direction.ASC, sortBys);
        } else {
            sort = Sort.by(Sort.Direction.DESC, sortBys);
        }
        return sort;
    }
}
