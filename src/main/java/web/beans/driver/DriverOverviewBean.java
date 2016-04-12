/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans.driver;

import main.domain.Car;
import main.service.CarService;
import web.model.DataTableModel;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import main.domain.Driver;
import main.service.DriverService;

/**
 * @author maikel
 */
@ManagedBean
@Named
@SessionScoped
public class DriverOverviewBean extends DataTableModel<DriverService, Driver> implements Serializable {

    @Inject
    private DriverService driverService;

    @Override
    protected DriverService getService() {
        return driverService;
    }

    @Override
    protected List<Driver> getData() {
        return driverService.getSortedFilteredAndPaged(getStartIndex(), getItemsPerPage(), getSortedOn(), getSortOrder(), getFilter());
    }

    @Override
    protected int getRowCount() {
        return driverService.getFilteredRowCount(getFilter());
    }

    @Override
    protected String getDefaultSort() {
        return "d.id";
    }

    public void sortById() {
        sort("d.id");
    }

    public void sortByLastName() {
        sort("d.lastName");
    }
    
    public void sortByFirstName() {
        sort("d.firstName");
    }
}
