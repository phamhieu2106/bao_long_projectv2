package org.example.sharedlibrary.base_constant;

import lombok.Getter;
import lombok.Setter;
import org.example.sharedlibrary.base_request.SortRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageConstant {

    public static final int PAGE_NUMBER = 0;
    public static final int PAGE_SIZE = 10;
    public static final String PAGE_SORT_TYPE = "ASC";
    public static final String PAGE_DEFAULT_VALUE = "";
    public static final String PAGE_DEFAULT_SORT_BYS = "id";
    public static final String PAGE_DEFAULT_FILTERS = null;
    public static final List<SortRequest> PAGE_DEFAULT_SORTS = List.of(new SortRequest[]{new SortRequest("id")});

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

    public static Sort getSortBys(List<SortRequest> sortRequests) {
        if (sortRequests == null || sortRequests.isEmpty()) {
            return null;
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (SortRequest sortRequest : sortRequests) {
            if (sortRequest.isSortAscending()) {
                orders.add(new Sort.Order(Sort.Direction.ASC, sortRequest.getFieldName()));
            } else {
                orders.add(new Sort.Order(Sort.Direction.DESC, sortRequest.getFieldName()));
            }
        }
        return Sort.by(orders);
    }
}
