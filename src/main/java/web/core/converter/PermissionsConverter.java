package web.core.converter;

import main.domain.enums.Permission;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

/**
 * Created by martijn on 12-5-2016.
 */
@FacesConverter(value = "permissionConverter")
public class PermissionsConverter extends EnumConverter {

    public PermissionsConverter() {
        super(Permission.class);
    }

}
