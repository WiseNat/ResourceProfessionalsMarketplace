package com.wise.resource.professionals.marketplace.component;

import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import lombok.Getter;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;

@Getter
public class ApprovalListBox extends ListBox {

    private final ApprovalEntity approval;

    @SneakyThrows
    public ApprovalListBox(ApprovalEntity approval) {
        super();

        this.approval = approval;

        init();
    }

    private void init() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy @ HH:mm");

        String accountType = approval.getAccount().getAccountType().getName();
        String name = approval.getAccount().getFirstName() + " " + approval.getAccount().getLastName();
        String email = approval.getAccount().getEmail();
        String date = dateFormat.format(approval.getDate());

        this.setTitleText(accountType + " Account Creation");
        this.setLeftSubtext(name + "\n" + email);
        this.setRightSubtext(date);
        this.removeImage();
    }
}
