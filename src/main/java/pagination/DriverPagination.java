/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pagination;

import java.util.ArrayList;
import java.util.List;
import main.domain.Driver;

/**
 *
 * @author Marijn
 */
public class DriverPagination extends Pagination  {

    private List<Driver> items = new ArrayList<>();

    public DriverPagination() {
        // empty constructor
    }

    public DriverPagination(int pageSize, int pageIndex) {
        super(pageSize, pageIndex);
    }
    
    public List<Driver> getItems() {
        return items;
    }

    public void setItems(List<Driver> items) {
        this.items = items;
    }
    
    public void addItem(Driver item) {
        this.items.add(item);
    }
    
}
