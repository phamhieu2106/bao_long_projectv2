package org.example.userdomain.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.enumeration.Department;
import org.example.sharedlibrary.enumeration.Role;
import org.example.userdomain.converter.DepartmentsConverter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Table
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {

    @Column(unique = true)
    String userCode;
    @Column(unique = true)
    String username;
    String password;
    @Enumerated(EnumType.STRING)
    Role role;
    String office;
    @Convert(converter = DepartmentsConverter.class)
    @Column(length = 1000)
    List<Department> departments;
    @Id
    String id;
    Boolean isDeleted;
    String createdBy;
    Date createdAt;

    public UserEntity(String id, Boolean isDeleted, String createdBy, Date createdAt, String userCode
            , String username, String password, Role role, String unit, List<Department> departments) {
        this.id = id;
        this.userCode = userCode;
        this.username = username;
        this.password = password;
        this.role = role;
        this.office = unit;
        this.departments = departments;
        this.isDeleted = isDeleted;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

}
