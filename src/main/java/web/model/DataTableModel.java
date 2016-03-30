package web.model;

import com.ceron.gatevvem.web.core.pagination.Paginator;
import main.dao.AbstractDao;
import domain.enums.SortOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * Base implementation for dataTable beans that need filterable and sortable
 * dataTables with pagination.
 *
 * @param <ServiceType> used to store a DataSourceService
 * @param <EntityType> used to store a domain Entity
 * @autor Sam
 */
public abstract class DataTableModel<ServiceType extends AbstractDao, EntityType> extends Paginator {

    private List<EntityType> displayedList;

    private String filter = "";
    private String sortedOn = "";
    private boolean sortIsAscending = true;

    protected EntityType entityToRemove;

    /**
     * @return get the service from a child Class.
     */
    protected abstract ServiceType getService();

    /**
     * Get data of a certain entityType from the database. This data request can
     * be manipulated by filters, sorts and pagination.
     *
     * @return List with the found data from the database.
     */
    protected abstract List<EntityType> getData();

    /**
     * @return The number of rows returned form a database query.
     */
    protected abstract int getRowCount();

    /**
     * Called when no sorting is specified.
     *
     * @return default sort values.
     */
    protected abstract String getDefaultSort();

    /**
     * Refresh the dataList the user can see, by re-applying filters, sorts and
     * pagination.
     *
     * @param sortOn Map with the name of an EntityType as the Key, and a field
     * of the given EntityType as the value.
     */
    private void refreshList(String sortOn) {
        if (displayedList == null) {
            displayedList = new ArrayList<>();
        }

        sortedOn = sortOn;

        displayedList.clear();
        displayedList.addAll(getData());
        setDataListTotalSize(getRowCount());
    }

    /**
     * Handle request for a filter of the displayed data.
     */
    public void filter() {
        refreshList(sortedOn);
        setCurrentPage(1);
    }

    /**
     * Reset filterValue and refresh the displayed data.
     */
    public void resetFilter() {
        filter = "";
        refreshList(sortedOn);
        setCurrentPage(1);
    }

    /**
     * Remove an EntityType.
     *
     * @param entity EntityType to remove.
     */
    public void remove(EntityType entity) {
        //TODO
        //getService().remove(entity);
        refreshList(sortedOn);
    }

    public void sort(String sortValue) {
        flipSortOrder();
        refreshList(sortValue);
    }

    private void flipSortOrder() {
        sortIsAscending = !sortIsAscending;
    }

    public void forceRefresh() {
        refreshList(sortedOn);
    }

    //<editor-fold defaultstate="open" desc="props">
    /**
     * Get the data that passed all the filters.
     *
     * @return The final list with data.
     */
    public List<EntityType> getDisplayedList() {
        if (displayedList == null) {
            displayedList = new ArrayList<>();

            if (sortedOn == null || sortedOn.isEmpty()) {
                sortedOn = getDefaultSort();
            }
            refreshList(sortedOn);
        }

        return displayedList;
    }

    public void setDisplayedList(List<EntityType> displayedList) {
        this.displayedList = displayedList;
    }

    /**
     * @return True if next sort is Ascending, false if next sort is Descending.
     */
    public boolean getSortIsAscending() {
        return sortIsAscending;
    }

    /**
     * @return SortOrder.TYPE based on the next sort.
     */
    public SortOrder getSortOrder() {
        return sortIsAscending ? SortOrder.ASCENDING : SortOrder.DESCENDING;
    }

    /**
     * @return the EntityType object plus the field which was last sorted on.
     */
    public String getSortedOn() {
        return sortedOn == null || sortedOn.isEmpty() ? getDefaultSort() : sortedOn;
    }

    public String getFilter() {
        return filter.trim();
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * Remove the pre-selected entity from the database, return to the previous
     * paginated page if the current page is empty.
     */
    public void removeSelectedEntity() {
        //TODO
        //getService().remove(entityToRemove);

        if (getItemsPerPage() * (getCurrentPage() - 1) + 1 == getDataListTotalSize()) {
            setCurrentPage(getCurrentPage() - 1);
        }

        refreshList(sortedOn);
    }

    /**
     * Set an entity to be removed later.
     *
     * @param entity The entity which might get removed
     */
    public void setEntityToRemove(EntityType entity) {
        this.entityToRemove = entity;
    }

    /**
     * Get the desired with for a button group containing action buttons like
     * edit and remove based on the permissions to execute these actions.
     *
     * @param value1 Permission to use first button.
     * @param value2 Permission to use second button.
     * @return String with the width of the button group.
     */
    public static String getActionButtonGroupWidth(boolean value1, boolean value2) {
        if (value1 && value2) {
            return "45px";
        } else if (value1 && !value2 || !value1 && value2) {
            return "22px";
        } else {
            return "0px";
        }
    }

    @Override
    public void beforePreviousPage() {
        super.beforePreviousPage();
        refreshList(sortedOn);
    }

    @Override
    public void previousPage() {
        super.previousPage();
        refreshList(sortedOn);
    }

    @Override
    public void currentPage() {
        super.currentPage();
        refreshList(sortedOn);
    }

    @Override
    public void nextPage() {
        super.nextPage();
        refreshList(sortedOn);
    }

    @Override
    public void afterNextPage() {
        super.afterNextPage();
        refreshList(sortedOn);
    }

    @Override
    public void firstPage() {
        super.firstPage();
        refreshList(sortedOn);
    }

    @Override
    public void lastPage() {
        super.lastPage();
        refreshList(sortedOn);
    }

    @Override
    public void setItemsPerPage(int itemsPerPage) {
        super.setItemsPerPage(itemsPerPage);
        refreshList(sortedOn);
    }
    //</editor-fold>
}
