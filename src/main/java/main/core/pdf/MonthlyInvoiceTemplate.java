package main.core.pdf;

import java.text.DecimalFormat;

import main.domain.Driver;
import main.domain.Invoice;

/**
 * @author Sam
 *         <p>
 *         Template used for an invoice to a @{code Driver} with the price that has to be paid for a certain month.
 *         </p>
 */
public class MonthlyInvoiceTemplate implements ITemplate {

    private final Invoice invoice;
    private final double distance;

    public MonthlyInvoiceTemplate(Invoice invoice, double distance) {
        this.invoice = invoice;
        this.distance = distance;
    }

    @Override
    public String parse() {
        Driver driver = invoice.getOwnership().getDriver();

        DecimalFormat decimalF = new DecimalFormat();
        decimalF.setMaximumFractionDigits(2);
        decimalF.setMinimumFractionDigits(2);
        decimalF.setGroupingUsed(false);

        StringBuilder sb = new StringBuilder();
        sb.append("<html>");

        sb.append("<body>");
        sb.append("<div class=\"container\">");
        sb.append("<table style=\"width:100%; font-size:11px;\">");
        sb.append("<tbody>");

        sb.append("<tr><td>");

        sb.append("<table style=\"width:100%; font-size:12px;\">");
        sb.append("<tbody>");

        sb.append("<tr>");
        sb.append("<td style=\"width:50%; padding-left:15px;\">").append(" ").append("</td>");
        sb.append("<td style=\"text-align:right;\">").append("Government of Portugal").append("</td>");
        sb.append("</tr>");

        sb.append("<tr>");
        sb.append("<td style=\"width:50%; padding-left:15px;\">").append(" ").append("</td>");
        sb.append("<td style=\"text-align:right;\">").append("Strada blanco 12").append("</td>");
        sb.append("</tr>");

        sb.append("<tr>");
        sb.append("<td style=\"width:50%; padding-left:15px;\">").append(" ").append("</td>");
        sb.append("<td style=\"text-align:right;\">").append("DF7723").append(" ").append("Lissabon").append("</td>");
        sb.append("</tr>");

        sb.append("<tr>");
        sb.append("<td style=\"width:50%; padding-left:15px;\">").append(" ").append("</td>");
        sb.append("<td style=\"text-align:right;\">").append("portugal.gov").append("</td>");
        sb.append("</tr>");

        sb.append("<tr>");
        sb.append("<td style=\"width:50%; padding-left:15px;\">").append(" ").append("</td>");
        sb.append("<td style=\"text-align:right;\">").append("tax@portugal.gov").append("</td>");
        sb.append("</tr>");

        sb.append("<tr><td style=\"color:white\">.</td></tr>");
        sb.append("<tr><td style=\"color:white\">.</td></tr>");
        sb.append("<tr><td style=\"color:white\">.</td></tr>");
        sb.append("<tr><td style=\"color:white\">.</td></tr>");
        sb.append("<tr><td style=\"color:white\">.</td></tr>");
        sb.append("<tr><td style=\"color:white\">.</td></tr>");
        sb.append("<tr><td style=\"color:white\">.</td></tr>");
        sb.append("<tr><td style=\"color:white\">.</td></tr>");
        sb.append("<tr><td style=\"color:white\">.</td></tr>");

        sb.append("<tr>");
        sb.append("<td style=\"width:50%; padding-left:15px;\">").append(driver.getFullName()).append("</td>");
        sb.append("<td style=\"text-align:right;\">").append("<strong>").append("Invoice").append("</strong>").append("</td>");
        sb.append("</tr>");

        sb.append("<tr>");
        sb.append("<td style=\"width:50%; padding-left:15px;\">").append(driver.getAddress().getStreetAndNr()).append("</td>");
        sb.append("</tr>");

        sb.append("<tr>");
        sb.append("<td style=\"width:50%; padding-left:15px;\">").append(driver.getAddress().getZipCode()).append(" ").append(driver.getAddress().getCity()).append("</td>");
        sb.append("<td style=\"text-align:right;\">").append(" ").append("</td>");
        sb.append("</tr>");

        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</td></tr>");

        sb.append("<tr><td style=\"color:white\">.</td></tr>");
        sb.append("<tr><td style=\"color:white\">.</td></tr>");
        sb.append("<tr><td style=\"color:white\">.</td></tr>");
        sb.append("<tr><td style=\"color:white\">.</td></tr>");
        sb.append("<tr><td style=\"color:white\">.</td></tr>");
        sb.append("<tr><td style=\"color:white\">.</td></tr>");
        sb.append("<tr><td style=\"color:white\">.</td></tr>");

        sb.append("<tr><td>");
        sb.append("<table style=\"width:100%; font-size:12px;\">");
        sb.append("<thead>");

        sb.append("<tr>");
        sb.append("<th>").append("Distance").append("</th>");
        sb.append("<th>").append("EnegyLabel").append("</th>");
        sb.append("<th>").append("Price").append("</th>");
        sb.append("</tr>");

        sb.append("</thead>");
        sb.append("<tbody>");

        sb.append("<tr>");
        sb.append("<td>").append(distance).append("</td>");
        sb.append("<td>").append(invoice.getOwnership().getCar().getRate().getName()).append("</td>");
        sb.append("<td>").append(decimalF.format(invoice.getTotalAmount())).append("</td>");
        sb.append("</tr>");

        sb.append("</tbody>");
        sb.append("</table>");

        sb.append("<div class=\"footer\" style=\"width:100%; font-size:12px;\">");
        sb.append("<hr/>");
        sb.append("<table style=\"width:100%; font-size:12px;\">");
        sb.append("<tbody>");

        for (int i = 0; i < 42; i++) {
            sb.append("<tr><td style=\"color:white\">.</td></tr>");
        }

        sb.append("</tbody>");
        sb.append("</table>");

        sb.append("<hr/>");
        sb.append("<p style=\"text-align:center;\">");
        sb.append("<strong>Tax nr: </strong>").append("2378936632").append(" - ");
        sb.append("</p>");
        sb.append("<p style=\"text-align:center;\">");
        sb.append("This invoice may be paid with Paypal");
        sb.append("</p>");
        sb.append("</div>");

        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</tbody>");
        sb.append("</table>");

        sb.append("</div>");
        sb.append("</body>");
        sb.append("</html>");

        String s = sb.toString();
        return sb.toString();
    }
}
