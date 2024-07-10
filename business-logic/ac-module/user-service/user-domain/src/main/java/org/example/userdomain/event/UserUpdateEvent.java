package org.example.userdomain.event;

import lombok.Getter;
import lombok.Setter;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.example.sharedlibrary.enumeration.Department;
import org.example.sharedlibrary.enumeration.Role;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserUpdateEvent extends BaseEvent {
    String userId;
    String password;
    Role role;
    String office;
    List<Department> departments;
    String createdBy;

    public UserUpdateEvent(Date timestamp, String createdBy, String userId, String userCode, String username
            , String password, Role role, String office, List<Department> departments) {
        super(timestamp, createdBy);
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.office = office;
        this.departments = departments;
    }
}
