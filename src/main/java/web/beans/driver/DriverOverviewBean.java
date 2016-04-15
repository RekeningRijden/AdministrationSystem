/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans.driver;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import main.domain.Driver;
import main.service.DriverService;
import web.model.DataTableModel;

/**
 * @author maikel
 */
@Named
@ViewScoped
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
