package org.example.demo2.validator;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import java.util.regex.Pattern;

@FacesValidator("birthDateValidator")
public class BirthDateValidator implements Validator {
    private static final Pattern PATTERN = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String date = (String) value;
        if (!PATTERN.matcher(date).matches()) {
            throw new ValidatorException(new FacesMessage("Date must be in DD/MM/YYYY format"));
        }
    }
}