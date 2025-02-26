package org.example.demo2.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("addressConverter")
public class AddressConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value; // Simple pass-through for now
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value != null ? value.toString() : "";
    }
}