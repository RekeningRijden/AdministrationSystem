package web.core.helpers;

import org.primefaces.context.RequestContext;

/**
 * @author Sam
 *         <p>
 *         Helper class providing methods for communicating with the front-end.
 *         </p>
 */
public final class FrontendHelper {

    private FrontendHelper() {
        //Utility class constructor cannot be called
    }

    /**
     * Display a smallbox in the upper right corner of the screen.
     *
     * @param title       of the smallbox.
     * @param description of the smallbox.
     * @param contentIcon placed in the bottom right corner of the smallbox
     *                    alongside the content.
     * @param smallIcon   placed in the bottom left corner of the smallbox.
     * @param color       of the smallbox body.
     */
    private static void displaySmallBox(String title, String description, String contentIcon, String smallIcon, String color) {
        StringBuilder sb = new StringBuilder();

        sb.append("$.smallBox({ title : \"").append(title).append("\"");
        sb.append(", content : \"<i class='").append(contentIcon).append("'></i> <i>").append(description).append("</i>\"");
        sb.append(", color : \"").append(color).append("\"");
        sb.append(", iconSmall : \"").append(smallIcon).append(" bounce animated\"");
        sb.append(", timeout : 4000 });");

        RequestContext.getCurrentInstance().execute(sb.toString());
    }

    /**
     * Display a smallbox with primary color in the upper right corner of the
     * screen.
     *
     * @param title of the smallbox
     */
    public static void displayPrimarySmallBox(String title) {
        displaySmallBox(title, "Zojuist", "fa fa-clock", "fa fa-check", "#6696E2");
    }

    /**
     * Display a smallbox with success color in the upper right corner of the
     * screen.
     *
     * @param title of the smallbox
     */
    public static void displaySuccessSmallBox(String title) {
        displaySmallBox(title, "Zojuist", "fa fa-clock", "fa fa-check", "#5F895F");
    }

    /**
     * Display a smallbox with warning color in the upper right corner of the
     * screen.
     *
     * @param title of the smallbox
     */
    public static void displayWarningSmallBox(String title) {
        displaySmallBox(title, "Zojuist", "fa fa-clock", "fa fa-info", "#FF9900");
    }

    /**
     * Display a smallbox with error color in the upper right corner of the
     * screen.
     *
     * @param title of the smallbox
     */
    public static void displayErrorSmallBox(String title) {
        displaySmallBox(title, "Zojuist", "fa fa-clock", "fa fa-remove", "#a90329");
    }

    /**
     * Display a bigbox in the bottom right corner of the screen.
     *
     * @param title       of the bigbox.
     * @param description of the bigbox.
     * @param contentIcon placed in the bottom right corner of the bigbox
     *                    alongside the content.
     * @param color       of the bigbox body.
     */
    private static void displayBigBox(String title, String description, String contentIcon, String color) {
        StringBuilder sb = new StringBuilder();

        sb.append("$.bigBox({ title : \"").append(title).append("\"");
        sb.append(", content : \"<i class='").append(contentIcon).append("'></i> <i>").append(description).append("</i>\"");
        sb.append(", color : \"").append(color).append("\"");
        sb.append(", icon : \"").append("").append(" bounce animated\"");
        sb.append(", timeout : 10000 });");

        RequestContext.getCurrentInstance().execute(sb.toString());
    }

    /**
     * Display a bigbox with primary color in the bottom right corner of the
     * screen.
     *
     * @param title       of the bigbox
     * @param description of the bigbox
     */
    public static void displayPrimaryBigBox(String title, String description) {
        displayBigBox(title, description, "fa fa-clock", "#36A9E0");
    }

    /**
     * Show a modal.
     *
     * @param modalName name of the modal to show.
     */
    public static void showModal(String modalName) {
        String executable = "$(\"#" + modalName + "\").modal(\"show\");";
        RequestContext.getCurrentInstance().execute(executable);
    }

    /**
     * Hide a modal.
     *
     * @param modalName name of the modal to hide.
     */
    public static void hideModal(String modalName) {
        String executable = "$(\"#" + modalName + "\").modal(\"hide\");";
        RequestContext.getCurrentInstance().execute(executable);
    }

    /**
     * Execute code on a widget to collapse or expand it.
     *
     * @param widgetId id of the widget to collapse.
     * @param var      option to reverse if statement.
     */
    private static void widget(String widgetId, String var) {
        StringBuilder sb = new StringBuilder();

        sb.append("if (").append(var).append("$(\"#");
        sb.append(widgetId).append("\").hasClass('jarviswidget-collapsed')) {");
        sb.append("$(\"#").append(widgetId).append(" a.jarviswidget-toggle-btn\").trigger(\"click\");");
        sb.append("}");

        RequestContext.getCurrentInstance().execute(sb.toString());
    }

    /**
     * Collapse a widget.
     *
     * @param widgetId id of the widget to collapse.
     */
    public static void collapseWidget(String widgetId) {
        widget(widgetId, "!");
    }

    /**
     * Expand a widget.
     *
     * @param widgetId id of the widget to expand.
     */
    public static void expandWidget(String widgetId) {
        widget(widgetId, "");
    }

    /**
     * Execute javascript code.
     *
     * @param executable code to execute.
     */
    public static void javascript(String executable) {
        RequestContext.getCurrentInstance().execute(executable);
    }

    /**
     * Update a view component with ajax.
     *
     * @param value id of the component to change.
     */
    public static void ajax(String value) {
        RequestContext.getCurrentInstance().update(value);
    }
}
