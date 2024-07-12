package org.example.userdomain.converter;

import org.example.sharedlibrary.base_converter.BaseConverter;
import org.example.sharedlibrary.enumeration.ac.Permission;

public class PermissionConverter extends BaseConverter<Permission> {
    public PermissionConverter() {
        super(Permission.class);
    }
}
