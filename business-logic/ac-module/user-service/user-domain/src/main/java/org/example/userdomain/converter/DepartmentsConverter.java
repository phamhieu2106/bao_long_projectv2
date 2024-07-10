package org.example.userdomain.converter;

import org.example.sharedlibrary.base_converter.BaseConverter;
import org.example.sharedlibrary.enumeration.Department;

public class DepartmentsConverter extends BaseConverter<Department> {

    public DepartmentsConverter() {
        super(Department.class);
    }
}
