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
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            return rateService.getByValue(value);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        return ((Rate) value).getRateValue();
    }
}
