package web.core.converter;

import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import main.domain.Driver;
import main.service.DriverService;

/**
 *
 * @author Emir Zahiragic
 */
@FacesConverter("driverConverter")
public class DriverConverter implements Converter, Serializable {
    
    @Inject
    private DriverService driverService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            return driverService.getByName(value);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        return ((Driver) value).getFullName();
    }
}
