/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans.car;

import main.domain.Car;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import main.service.CarService;
import web.model.DataTableModel;

/**
 *
 * @author maikel
 */
@ManagedBean
@Named
@ViewScoped
public class CarOverviewBean extends DataTableModel<CarService, Car> implements Serializable{
    
    @Inject
    private CarService carService;

    
    @Override
    protected CarService getService() {
        return carService;
    }

    @Override
    protected List<Car> getData() {
        return carService.getSortedFilteredAndPaged(getStartIndex(), getItemsPerPage(), getSortedOn(), getSortOrder(), getFilter());
    }

    @Override
    protected int getRowCount() {
        return carService.getFilteredRowCount(getFilter());
    }

    @Override
    protected String getDefaultSort() {
        return "c.id";
    }
    
    public void sortById() {
        sort("c.id");
    }
    
    public void sortByDriver() {
        sort("d.lastName");
    }
    
}
