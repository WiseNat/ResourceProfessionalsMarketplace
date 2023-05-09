package com.wise.resource.professionals.marketplace.component;

import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.SubRoleEntity;
import com.wise.resource.professionals.marketplace.util.ComponentUtil;
import lombok.Getter;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: This..

@Getter
public class ReturnResourceListBox extends ListBox {

    private final AccountEntity accountEntity;

    @SneakyThrows
    public ReturnResourceListBox(AccountEntity accountEntity) {
        super();

        this.accountEntity = accountEntity;

        init();
    }

    private void init() {
        ComponentUtil componentUtil = new ComponentUtil();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String name = accountEntity.getFirstName() + " " + accountEntity.getLastName();
        String role = accountEntity.getResource().getMainRole().getName();
        String band = accountEntity.getResource().getBanding().getName();
        String client = accountEntity.getResource().getLoanedClient();

        SubRoleEntity subRoleEntity = accountEntity.getResource().getSubRole();
        if (subRoleEntity != null) {
            role += ", " + subRoleEntity.getName();
        }

        String rightSubText = "Loaned out to " + client + "\n";

        Date dueDate = accountEntity.getResource().getAvailabilityDate();
        String dueDateString = dateFormat.format(accountEntity.getResource().getAvailabilityDate());

        if (dueDate.before(new Date(System.currentTimeMillis()))) {
            rightSubText += "Overdue since " + dueDateString;
            componentUtil.safeAddStyleClass(this.getRightSubtext(), "negative-label");
        } else {
            rightSubText += "Available " + dueDateString;
        }

        this.setTitleText(name);
        this.setLeftSubtext(role + "\n" + band);
        this.setRightSubtext(rightSubText);

        this.removeMainImage();
        this.removeSubImage();
    }
}
