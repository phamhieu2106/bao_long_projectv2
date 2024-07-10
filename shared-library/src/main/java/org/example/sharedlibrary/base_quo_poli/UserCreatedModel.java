package org.example.sharedlibrary.base_quo_poli;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedModel {
    String username;
    String office;
    String apartment;
}
