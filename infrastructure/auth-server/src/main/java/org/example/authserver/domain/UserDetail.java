package org.example.authserver.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseEntity;
import org.example.sharedlibrary.enumeration.Department;
import org.example.sharedlibrary.enumeration.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetail extends BaseEntity implements UserDetails {

    String userCode;
    String username;
    String password;
    Role role;
    String office;
    List<Department> departments;
    String id;

    public UserDetail(String id, Boolean isDeleted, String createdBy, Date createdAt, String userCode
            , String username, String password, Role role, String unit, List<Department> departments) {
        super(id, isDeleted, createdBy, createdAt);
        this.userCode = userCode;
        this.username = username;
        this.password = password;
        this.role = role;
        this.office = unit;
        this.departments = departments;
    }

    public UserDetail(String userUsername) {
        this.username = userUsername;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
