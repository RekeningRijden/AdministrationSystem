package web.beans.car;

import main.domain.Car;
import main.service.CarService;
import web.model.DataTableModel;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.List;

/**
 * @author maikel
 */
@Named
@ViewScoped
public class CarOverviewBean extends DataTableModel<CarService, Car> implements Serializable {

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
    
    public void sortByLicencePlate() {
        sort("c.licencePlate");
    }
}
