package org.example.demo2.validator;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("balanceValidator")
public class BalanceValidator implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Double balance = (Double) value;
        if (balance < 0) {
            throw new ValidatorException(new FacesMessage("Balance must be positive"));
        }
    }
}