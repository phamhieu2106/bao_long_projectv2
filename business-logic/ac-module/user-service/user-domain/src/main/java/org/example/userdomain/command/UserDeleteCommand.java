package org.example.userdomain.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseCommand;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDeleteCommand extends BaseCommand {
    String userId;
    String createdBy;

    public UserDeleteCommand(String userId) {
        this.userId = userId;
    }
}
