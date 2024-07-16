package org.example.quotationdomain.event;

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
public class QuotationChangeStatusEvent extends BaseEvent {
    String id;
    QuotationStatus quotationStatus;
    List<UserModel> userModels;
    String approvedBy;
    Date approvedAt;
    Role lastUserRoleUpdate;

    public QuotationChangeStatusEvent(Date timestamp, String createdBy, String id, QuotationStatus quotationStatus
            , List<UserModel> userModels, String approvedBy, Date approvedAt) {
        super(timestamp, createdBy);
        this.id = id;
        this.quotationStatus = quotationStatus;
        this.userModels = userModels;
        this.approvedBy = approvedBy;
        this.approvedAt = approvedAt;
    }
}
