package org.example.sharedlibrary.base_constant;

import java.util.UUID;

public class GenerateConstant {

    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
