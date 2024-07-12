package org.example.sharedlibrary.enumeration.ac;

import lombok.Getter;

@Getter
public enum Role {
    EMPLOYEE(1),
    SPECIALIST(1),
    DEPARTMENT_VICE(2),
    DEPARTMENT_MANAGER(2),
    DIRECTOR_VICE(3),
    DIRECTOR(3),
    GENERAL_MANAGER(4);

    private final int value;

    Role(int value) {
        this.value = value;
    }
}
