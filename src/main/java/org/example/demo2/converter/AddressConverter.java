package org.example.demo2.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("addressConverter")
public class AddressConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value; // Store as-is, parse in bean if needed
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return (String) value; // Display as-is
    }
}