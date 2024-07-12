package org.example.userdomain.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseCommand;
import org.example.sharedlibrary.enumeration.ac.Department;
import org.example.sharedlibrary.enumeration.ac.Permission;
import org.example.sharedlibrary.enumeration.ac.Role;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateCommand extends BaseCommand {
    String userId;
    String password;
    Role role;
    String office;
    Department department;
    String createdBy;
    List<Permission> permissions;
}
