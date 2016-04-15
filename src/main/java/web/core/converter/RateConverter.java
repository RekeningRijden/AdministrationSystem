package web.core.converter;

import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import main.domain.Rate;
import main.service.RateService;

/**
 *
 * @author Emir Zahiragic
 */
@FacesConverter("rateConverter")
public class RateConverter implements Converter, Serializable {
    
    @Inject
    private RateService rateService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String name) {
        if (name == null || name.isEmpty()) {
            return null;
        } else {
            return rateService.getByName(name);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        return ((Rate) value).getName();
    }
}
