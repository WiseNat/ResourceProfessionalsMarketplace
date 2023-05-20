package com.wise.resource.professionals.marketplace.component;

import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import lombok.Getter;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;

/**
 * An extension of {@link ListBox} which provides automatic initialisation specific to approvals. It also stores the
 * given {@link ApprovalEntity} for later use.
 */
@Getter
public class ApprovalListBox extends ListBox {

    private final ApprovalEntity approval;

    /**
     * @param approval the associated approval for this {@code ApprovalListBox}
     */
    @SneakyThrows
    public ApprovalListBox(ApprovalEntity approval) {
        super();

        this.approval = approval;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy @ HH:mm");

        String accountType = approval.getAccount().getAccountType().getName();
        String name = approval.getAccount().getFirstName() + " " + approval.getAccount().getLastName();
        String email = approval.getAccount().getEmail();
        String date = dateFormat.format(approval.getDate());

        this.setTitleText(accountType + " Account Creation");
        this.setLeftSubtext(name + "\n" + email);
        this.setRightSubtext(date);

        this.removeMainImage();
        this.removeSubImage();
    }
}
