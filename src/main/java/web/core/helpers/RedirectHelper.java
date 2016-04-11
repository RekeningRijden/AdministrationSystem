package web.core.helpers;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bas
 */
public final class RedirectHelper {

    private RedirectHelper() {
        //Utility class constructor cannot be called
    }

    public static void redirect(String relativeUrl) {
        redirect(relativeUrl, true);
    }

    private static void redirect(String relativeUrl, boolean appendRelativeUrl) {
        try {
            String url = appendRelativeUrl ? ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getContextPath()
                    + relativeUrl : relativeUrl;
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(RedirectHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
