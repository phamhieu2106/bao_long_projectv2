package org.example.userdomain.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.example.sharedlibrary.enumeration.Department;
import org.example.sharedlibrary.enumeration.Role;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateEvent extends BaseEvent {
    String userId;
    String userCode;
    String username;
    String password;
    Role role;
    String office;
    List<Department> departments;
    String createdBy;

    public UserCreateEvent(Date timestamp, String createdBy, String userId, String userCode, String username
            , String password, Role role, String office, List<Department> departments) {
        super(timestamp, createdBy);
        this.userId = userId;
        this.userCode = userCode;
        this.username = username;
        this.password = password;
        this.role = role;
        this.office = office;
        this.departments = departments;
    }
}
