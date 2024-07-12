package org.example.sharedlibrary.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.enumeration.ac.Permission;
import org.example.sharedlibrary.enumeration.ac.Role;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    String username;
    Role role;
    List<Permission> permissions;
}
