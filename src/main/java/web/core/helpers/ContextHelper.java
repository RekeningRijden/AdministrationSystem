package com.ceron.gatevvem.web.core.helper;

import javax.faces.context.FacesContext;

/**
 * @author Sam
 */
public class ContextHelper {

    public static boolean isAjaxRequest() {
        return FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest();
    }
}
