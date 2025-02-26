package org.example.demo2.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;@FacesConverter("balanceConverter")
public class BalanceConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        // Remove any non-numeric characters (like " MAD" or spaces)
        value = value.replace(" MAD", "").trim();

        // Replace the comma with a period for proper decimal conversion
        value = value.replace(",", ".");

        try {
            // Convert the cleaned-up value to Double
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            // Handle parsing failure
            throw new ConverterException("Invalid balance format: " + value, e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        // Simply return the value as a formatted string (without " MAD")
        return String.format("%.2f", (Double) value);
    }
}
