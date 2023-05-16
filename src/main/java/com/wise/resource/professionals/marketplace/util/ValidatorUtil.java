package com.wise.resource.professionals.marketplace.util;

import javafx.scene.control.Control;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

@Component
public class ValidatorUtil {

    @Autowired
    ComponentUtil componentUtil;

    public <T> String getFieldFromConstraintViolation(ConstraintViolation<T> constraintViolation) {
        return constraintViolation.getPropertyPath().toString();
    }

    public void markControlNegative(Control control, String negativeStyleClass) {
        componentUtil.safeAddStyleClass(control, negativeStyleClass);
    }

    public void markControlPositive(Control control, String negativeStyleClass) {
        control.getStyleClass().remove(negativeStyleClass);
    }

    public <TO> void markControlAgainstValidatedTO(
            Set<ConstraintViolation<TO>> violations,
            HashMap<String, Control> toFieldToControl,
            String negativeStyleClass) {

        Collection<Control> validControls = toFieldToControl.values();

        for (ConstraintViolation<TO> violation : violations) {
            String field = getFieldFromConstraintViolation(violation);

            if (toFieldToControl.containsKey(field)) {
                Control control = toFieldToControl.get(field);

                markControlNegative(control, negativeStyleClass);
                validControls.remove(control);
            }
        }

        for (Control control : validControls) {
            markControlPositive(control, negativeStyleClass);

        }
    }
}
