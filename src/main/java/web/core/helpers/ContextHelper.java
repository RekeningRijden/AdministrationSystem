package web.core.helpers;

import javax.faces.context.FacesContext;

/**
 * @author Sam
 */
public final class ContextHelper {

    private ContextHelper() {
        //Utility class constructor cannot be called
    }

    /**
     * @return true if the last http request was an Ajax request.
     */
    public static boolean isAjaxRequest() {
        return FacesContext.getCurrentInstance() != null && FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest();
    }
}
