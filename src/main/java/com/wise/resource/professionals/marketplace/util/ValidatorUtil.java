package com.wise.resource.professionals.marketplace.util;

import com.wise.resource.professionals.marketplace.to.InvalidFieldsAndDataTO;
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
    private ComponentUtil componentUtil;

    public <T> String[] getFieldsFromConstraintViolations(Set<ConstraintViolation<T>> constraintViolations) {
        String[] fields = new String[constraintViolations.size()];

        int i = -1;
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            i++;

            fields[i] = getFieldFromConstraintViolation(constraintViolation);
        }

        return fields;
    }

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
            HashMap<String, Control> fieldToControl,
            String negativeStyleClass) {

        Collection<Control> validControls = fieldToControl.values();

        for (ConstraintViolation<TO> violation : violations) {
            String field = getFieldFromConstraintViolation(violation);

            if (fieldToControl.containsKey(field)) {
                Control control = fieldToControl.get(field);

                markControlNegative(control, negativeStyleClass);
                validControls.remove(control);
            }
        }

        for (Control control : validControls) {
            markControlPositive(control, negativeStyleClass);
        }
    }

    public void markControlAgainstValidatedTO(
            String[] fields,
            HashMap<String, Control> fieldToControl,
            String negativeStyleClass) {

        Collection<Control> validControls = fieldToControl.values();

        for (String field : fields) {
            if (fieldToControl.containsKey(field)) {
                Control control = fieldToControl.get(field);

                markControlNegative(control, negativeStyleClass);
                validControls.remove(control);
            }
        }

        for (Control control : validControls) {
            markControlPositive(control, negativeStyleClass);
        }
    }

    public <T> InvalidFieldsAndDataTO<T> populateInvalidFieldsAndDataTO(Set<ConstraintViolation<T>> violations, T data) {
        InvalidFieldsAndDataTO<T> output = new InvalidFieldsAndDataTO<>();

        if (violations.size() > 0) {
            output.setInvalidFields(getFieldsFromConstraintViolations(violations));
            output.setData(null);
        } else {
            output.setInvalidFields(new String[]{});

            output.setData(data);
        }

        return output;
    }
}
