package main.core.pdf;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import main.core.helper.NumberHelper;
import main.domain.simulation.CarTracker;

/**
 * @author Sam
 *         <p>
 *         Template used for an invoice to a @{code Driver} with the price that has to be paid for a certain month.
 *         </p>
 */
public class MonthlyInvoiceTemplate implements ITemplate {

    private final CarTracker carTracker;
    private final BigDecimal price;

    public MonthlyInvoiceTemplate(CarTracker carTracker, BigDecimal price) {
        this.carTracker = carTracker;
        this.price = price;
    }

    @Override
    public String parse() {
        DecimalFormat decimalF = new DecimalFormat();
        decimalF.setMaximumFractionDigits(2);
        decimalF.setMinimumFractionDigits(0);
        decimalF.setGroupingUsed(false);

        StringBuilder sb = new StringBuilder();
        sb.append("<html>");

        sb.append("<body>");
        sb.append("<div class=\"container\">");

        sb.append("Price: ").append(NumberHelper.parseToString(price));

        sb.append("</div>");
        sb.append("</body>");
        sb.append("</html>");

        return sb.toString();
    }
}
