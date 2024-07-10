package org.example.userdomain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseEvent;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDeleteEvent extends BaseEvent {
    String userId;
    String createdBy;

    public UserDeleteEvent(Date timestamp, String createdBy, String userId) {
        super(timestamp, createdBy);
        this.userId = userId;
    }
}
