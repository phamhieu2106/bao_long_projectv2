package org.example.sharedlibrary.base_class;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseEvent {
    private Date timestamp;
    private String createdBy;

    public BaseEvent(Date timestamp, String createdBy) {
        this.timestamp = timestamp;
        this.createdBy = createdBy;
    }

    public BaseEvent(Date timestamp) {
        this.timestamp = timestamp;
    }
}
