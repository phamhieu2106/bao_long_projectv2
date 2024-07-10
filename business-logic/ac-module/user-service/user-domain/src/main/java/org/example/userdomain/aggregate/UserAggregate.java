package org.example.userdomain.aggregate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseAggregate;
import org.example.sharedlibrary.base_constant.GenerateConstant;
import org.example.sharedlibrary.enumeration.Department;
import org.example.sharedlibrary.enumeration.Role;
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
    List<Department> departments;
    String createdBy;

    public UserCreateEvent applyCreate(UserCreateCommand command) {
        this.userId = GenerateConstant.generateId();
        this.username = command.getUsername();
        this.password = command.getPassword();
        this.role = command.getRole();
        this.office = command.getOffice();
        this.departments = command.getDepartments();
        this.createdBy = command.getCreatedBy();

        return new UserCreateEvent(
                new Date(),
                this.createdBy,
                this.userId,
                this.userCode,
                this.username,
                this.password,
                this.role,
                this.office,
                this.departments
        );
    }

    public UserUpdateEvent applyUpdate(UserUpdateCommand command) {
        this.userId = GenerateConstant.generateId();
        this.password = command.getPassword();
        this.role = command.getRole();
        this.office = command.getOffice();
        this.departments = command.getDepartments();
        this.createdBy = command.getCreatedBy();

        return new UserUpdateEvent(
                new Date(),
                this.createdBy,
                this.userId,
                this.userCode,
                this.username,
                this.password,
                this.role,
                this.office,
                this.departments
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
