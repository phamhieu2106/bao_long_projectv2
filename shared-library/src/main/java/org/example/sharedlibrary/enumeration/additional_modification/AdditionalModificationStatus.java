package org.example.sharedlibrary.enumeration.additional_modification;

import lombok.Getter;

@Getter
public enum AdditionalModificationStatus {
    REQUIRE_INFORMATION,
    DRAFTING,
    AWAIT_APPROVE,
    APPROVED,
    REJECTED,
    UNDONE
}
