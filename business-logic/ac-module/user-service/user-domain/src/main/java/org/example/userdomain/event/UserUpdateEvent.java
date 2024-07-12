package org.example.userdomain.event;

import lombok.Getter;
import lombok.Setter;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.example.sharedlibrary.enumeration.ac.Department;
import org.example.sharedlibrary.enumeration.ac.Permission;
import org.example.sharedlibrary.enumeration.ac.Role;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserUpdateEvent extends BaseEvent {
    String userId;
    String password;
    Role role;
    String office;
    Department department;
    String createdBy;
    List<Permission> permissions;

    public UserUpdateEvent(Date timestamp, String createdBy, String userId, String userCode, String username
            , String password, Role role, String office, Department department, List<Permission> permissions) {
        super(timestamp, createdBy);
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.office = office;
        this.department = department;
        this.permissions = permissions;
    }
}
