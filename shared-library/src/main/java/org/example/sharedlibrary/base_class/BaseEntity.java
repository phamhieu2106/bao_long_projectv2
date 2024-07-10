package org.example.sharedlibrary.base_class;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {
    private String id;
    private Boolean isDeleted;
    private String createdBy;
    private Date createdAt;
}
