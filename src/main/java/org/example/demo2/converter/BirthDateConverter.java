package org.example.demo2.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@FacesConverter("birthDateConverter")
public class BirthDateConverter implements Converter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return LocalDate.parse(value, FORMATTER);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return FORMATTER.format((LocalDate) value);
    }
}