package web.core.pagination;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used a an interface to the Paginator when a data list is already
 * available.
 *
 * @author Sam
 * @param <T> EntityType used in the dataList.
 */
public class ListPaginator<T> extends Paginator {

    private List<T> dataList;

    public ListPaginator(List<T> dataList) {
        updateList(dataList);
    }

    /**
     * Create a reference to the original data list and set pass the size of the
     * data list to the super class.
     *
     * @param dataList list to create a reference to.
     */
    public void updateList(List<T> dataList) {
        this.dataList = dataList;
        setDataListTotalSize(dataList.size());
    }

    /**
     * @return a part of the total data list calculated by the current page and
     * number of items allowed on screen.
     */
    public List<T> toPaginatedList() {
        List<T> tempList = new ArrayList<>();

        int endIndex = getEndIndex();
        if (endIndex > dataList.size() - 1) {
            endIndex = dataList.size() - 1;
        }

        for (int i = getStartIndex(); i <= endIndex; i++) {
            tempList.add(dataList.get(i));
        }

        return tempList;
    }

    /**
     * @return true if the total data list is empty.
     */
    public boolean isEmpty() {
        return dataList.isEmpty();
    }
}
