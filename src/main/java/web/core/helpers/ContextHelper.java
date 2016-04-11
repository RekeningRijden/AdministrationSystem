package web.core.helpers;

import javax.faces.context.FacesContext;

/**
 * @author Sam
 */
public final class ContextHelper {

    private ContextHelper() {
        //Utility class constructor cannot be called
    }

    public static boolean isAjaxRequest() {
        return FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest();
    }
}
