package org.example.sharedlibrary.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.enumeration.InChargeRole;

import java.util.Date;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UserInChargeModel {
    String username;
    String office;
    String apartment;
    InChargeRole inChargeRole;
    Date inChargeFrom;
    Date inChargeTo;
}
