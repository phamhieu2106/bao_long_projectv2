package org.example.userdomain.aggregate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseAggregate;
import org.example.sharedlibrary.base_constant.GenerateConstant;
import org.example.sharedlibrary.enumeration.ac.Department;
import org.example.sharedlibrary.enumeration.ac.Permission;
import org.example.sharedlibrary.enumeration.ac.Role;
import org.example.userdomain.command.UserCreateCommand;
import org.example.userdomain.command.UserDeleteCommand;
import org.example.userdomain.command.UserUpdateCommand;
import org.example.userdomain.event.UserCreateEvent;
import org.example.userdomain.event.UserDeleteEvent;
import org.example.userdomain.event.UserUpdateEvent;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAggregate extends BaseAggregate {

    String userId;
    String userCode;
    String username;
    String password;
    Role role;
    String office;
    Department department;
    List<Permission> permissions;
    String createdBy;

    public UserCreateEvent applyCreate(UserCreateCommand command) {
        this.userId = GenerateConstant.generateId();
        this.username = command.getUsername();
        this.password = command.getPassword();
        this.role = command.getRole();
        this.office = command.getOffice();
        this.department = command.getDepartment();
        this.createdBy = command.getCreatedBy();
        this.permissions = command.getPermissions();

        return new UserCreateEvent(
                new Date(),
                this.createdBy,
                this.userId,
                this.userCode,
                this.username,
                this.password,
                this.role,
                this.office,
                this.department,
                this.permissions
        );
    }

    public UserUpdateEvent applyUpdate(UserUpdateCommand command) {
        this.userId = GenerateConstant.generateId();
        this.password = command.getPassword();
        this.role = command.getRole();
        this.office = command.getOffice();
        this.department = command.getDepartment();
        this.createdBy = command.getCreatedBy();
        this.permissions = command.getPermissions();

        return new UserUpdateEvent(
                new Date(),
                this.createdBy,
                this.userId,
                this.userCode,
                this.username,
                this.password,
                this.role,
                this.office,
                this.department,
                this.permissions
        );
    }

    public UserDeleteEvent applyDelete(UserDeleteCommand command) {
        this.userId = command.getUserId();
        this.createdBy = command.getCreatedBy();

        return new UserDeleteEvent(
                new Date(),
                this.userId,
                this.createdBy
        );

    }
}
