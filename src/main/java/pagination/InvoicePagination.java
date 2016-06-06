/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pagination;

import java.util.ArrayList;
import java.util.List;
import main.domain.Invoice;

/**
 *
 * @author Marijn
 */
public class InvoicePagination extends Pagination  {

    private List<Invoice> items = new ArrayList<>();

    public InvoicePagination() {
        // empty constructor
    }

    public InvoicePagination(int pageSize, int pageIndex) {
        super(pageSize, pageIndex);
    }
    
    public List<Invoice> getItems() {
        return items;
    }

    public void setItems(List<Invoice> items) {
        this.items = items;
    }
    
    public void addItem(Invoice item) {
        this.items.add(item);
    }
    
}
