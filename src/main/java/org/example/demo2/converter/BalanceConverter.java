package org.example.demo2.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("balanceConverter")
public class BalanceConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return Double.parseDouble(value.replace(" MAD", ""));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.format("%.2f MAD", (Double) value);
    }
}