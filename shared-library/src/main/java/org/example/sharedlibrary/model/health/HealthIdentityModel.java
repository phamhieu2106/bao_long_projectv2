package org.example.sharedlibrary.model.health;

import lombok.Getter;
import lombok.Setter;
import org.example.sharedlibrary.enumeration.HealthIdentityType;

@Getter
@Setter
public class HealthIdentityModel {
    HealthIdentityType identityType;
    String identityNumber;
}
