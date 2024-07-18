package org.example.quotationdomain.event.status;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.example.sharedlibrary.enumeration.QuotationStatus;
import org.example.sharedlibrary.enumeration.ac.Role;
import org.example.sharedlibrary.model.UserModel;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class QuotationChangeToAwaitApproveStatusEvent extends BaseEvent {
    final QuotationStatus quotationStatus = QuotationStatus.AWAIT_APPROVE;
    String quotationId;
    List<UserModel> userModels;
    Role lastUserRoleUpdate;

    public QuotationChangeToAwaitApproveStatusEvent(Date timestamp, String createdBy, String quotationId, List<UserModel> userModels, Role lastUserRoleUpdate) {
        super(timestamp, createdBy);
        this.quotationId = quotationId;
        this.userModels = userModels;
        this.lastUserRoleUpdate = lastUserRoleUpdate;
    }
}
