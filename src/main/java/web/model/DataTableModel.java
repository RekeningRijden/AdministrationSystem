package web.model;

import com.ceron.gatevvem.web.core.pagination.Paginator;
import main.dao.AbstractDao;
import main.domain.IEntity;
import main.domain.enums.SortOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Base implementation for filterable and sortable dataTables with pagination.
 * <p>
 * A bean or model can extend this model to inherit pagination capabilities and
 * only has to implement the abstract methods to deliver the data and this class
 * takes care of the lifecycle.
 *
 * @param <ServiceType> used to store a DataSourceService
 * @param <EntityType> used to store a domain Entity
 * @author Sam
 */
public abstract class DataTableModel<ServiceType extends AbstractDao<EntityType>, EntityType extends IEntity> extends Paginator {

    /**
     * List containing all the filtered, sorted and paginated data.
     */
    private List<EntityType> displayedList;

    private String filter = "";
    private String sortedOn = "";
    private boolean sortIsAscending = true;

    protected EntityType entityToRemove;

    /**
     * @return the service with database access.
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
     * Refresh the data list the user can see, by re-applying filters, sorts and
     * pagination.
     *
     * @param sortOn the database column name to sort on.
     */
    private void refreshList(String sortOn) {
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
        getService().remove(entity);
        refreshList(sortedOn);
    }

    /**
     * Turn the sort order around an refresh the data list.
     *
     * @param sortValue the database column name to sort on.
     */
    protected void sort(String sortValue) {
        flipSortOrder();
        refreshList(sortValue);
    }

    private void flipSortOrder() {
        sortIsAscending = !sortIsAscending;
    }

    protected void forceRefresh() {
        refreshList(sortedOn);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
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

    /**
     * @return True if next sort is Ascending, false if next sort is Descending.
     */
    public boolean getSortIsAscending() {
        return sortIsAscending;
    }

    /**
     * @return SortOrder.TYPE based on the next sort.
     */
    protected SortOrder getSortOrder() {
        return sortIsAscending ? SortOrder.ASCENDING : SortOrder.DESCENDING;
    }

    /**
     * @return the EntityType object plus the field which was last sorted on.
     */
    public String getSortedOn() {
        return sortedOn == null || sortedOn.isEmpty() ? getDefaultSort() : sortedOn;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter.trim();
    }

    /**
     * Remove the pre-selected entity from the database, return to the previous
     * paginated page if the current page is empty.
     */
    public void removeSelectedEntity() {
        getService().remove(entityToRemove);

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