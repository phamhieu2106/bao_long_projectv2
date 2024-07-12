package org.example.userdomain.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.example.sharedlibrary.enumeration.ac.Department;
import org.example.sharedlibrary.enumeration.ac.Permission;
import org.example.sharedlibrary.enumeration.ac.Role;

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
    Department department;
    String createdBy;
    List<Permission> permissions;

    public UserCreateEvent(Date timestamp, String createdBy, String userId, String userCode, String username
            , String password, Role role, String office, Department department, List<Permission> permissions) {
        super(timestamp, createdBy);
        this.userId = userId;
        this.userCode = userCode;
        this.username = username;
        this.password = password;
        this.role = role;
        this.office = office;
        this.department = department;
        this.permissions = permissions;
    }
}
