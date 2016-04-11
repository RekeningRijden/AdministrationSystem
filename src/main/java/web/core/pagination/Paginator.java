package web.core.pagination;

/**
 * @author Sam
 */
public abstract class Paginator {

    private int currentPage = 1;
    private int itemsPerPage = 15;
    private int dataListTotalSize;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Set current page to the first page.
     */
    public void firstPage() {
        currentPage = 1;
    }

    /**
     * Set current page to the last page.
     */
    public void lastPage() {
        currentPage = getLastPageNumber();
    }

    /**
     * @return number of the last page of this pagination.
     */
    public int getLastPageNumber() {
        return (int) Math.ceil((double) dataListTotalSize / (double) itemsPerPage);
    }

    /**
     * Go to the next page.
     */
    public void nextPage() {
        if (currentPage > 3) {
            if (currentPage != getLastPageNumber() - 1) {
                if (currentPage == getLastPageNumber()) {
                    currentPage--;
                } else {
                    currentPage++;
                }
            }
        } else {
            currentPage = 4;
        }
    }

    /**
     * Go to the page after the next page.
     */
    public void afterNextPage() {
        if (currentPage > 3) {
            if (currentPage != getLastPageNumber()) {
                if (currentPage != getLastPageNumber() - 1) {
                    currentPage += 2;
                } else {
                    currentPage++;
                }
            }
        } else {
            currentPage = 5;
        }
    }

    public void currentPage() {
        if (currentPage < 3) {
            currentPage = 3;
        } else {
            if (currentPage == getLastPageNumber() - 1) {
                currentPage--;
            }

            if (currentPage == getLastPageNumber()) {
                currentPage -= 2;
            }
        }
    }

    /**
     * Go to the previous page.
     */
    public void previousPage() {
        if (currentPage > 3) {
            currentPage--;
        } else {
            currentPage = 2;
        }
    }

    /**
     * Go to the page before the previous page.
     */
    public void beforePreviousPage() {
        if (currentPage > 3) {
            currentPage -= 2;
        } else {
            currentPage = 1;
        }
    }

    /**
     * @return number that should be displayed on the pagination button rows
     * first button.
     */
    public int getFirstButtonNumber() {
        if (currentPage > 3) {
            if (currentPage == getLastPageNumber() || currentPage == getLastPageNumber() - 1) {
                return getLastPageNumber() - 4;
            }
        } else {
            return 1;
        }
        return currentPage - 2;
    }

    /**
     * @return number that should be displayed on the pagination button rows
     * second button.
     */
    public int getSecondButtonNumber() {
        if (currentPage > 3) {
            if (currentPage == getLastPageNumber() || currentPage == getLastPageNumber() - 1) {
                return getLastPageNumber() - 3;
            }
        } else {
            return 2;
        }
        return currentPage - 1;
    }

    /**
     * @return number that should be displayed on the pagination button rows
     * third button.
     */
    public int getThirdButtonNumber() {
        if (currentPage > 3) {
            if (currentPage == getLastPageNumber() || currentPage == getLastPageNumber() - 1) {
                return getLastPageNumber() - 2;
            }
        } else {
            return 3;
        }

        return currentPage;
    }

    /**
     * @return number that should be displayed on the pagination button rows
     * fourth button.
     */
    public int getFourthButtonNumber() {
        if (currentPage > 3) {
            if (currentPage == getLastPageNumber() || currentPage == getLastPageNumber() - 1) {
                return getLastPageNumber() - 1;
            }
        } else {
            return 4;
        }

        return currentPage + 1;
    }

    /**
     * @return number that should be displayed on the pagination button rows
     * fifth button.
     */
    public int getFifthButtonNumber() {
        if (currentPage > 3) {
            if (currentPage == getLastPageNumber() || currentPage == getLastPageNumber() - 1) {
                return getLastPageNumber();
            }
        } else {
            return 5;
        }

        return currentPage + 2;
    }

    /**
     * @param buttonNumber
     * @return true if the displayed button number is greater than the number of
     * the last page.
     */
    public boolean isButtonDisabled(int buttonNumber) {
        return buttonNumber > getLastPageNumber();
    }

    public boolean isPrevNextButtonDisabled() {
        return dataListTotalSize <= itemsPerPage || dataListTotalSize == 0;
    }

    /**
     * @param buttonNumber
     * @return true if the displayed button number equals the current page
     * number.
     */
    public boolean isButtonCurrentPage(int buttonNumber) {
        int displayedButtonNumber;

        switch (buttonNumber) {
            case 1:
                displayedButtonNumber = getFirstButtonNumber();
                break;
            case 2:
                displayedButtonNumber = getSecondButtonNumber();
                break;
            case 3:
                displayedButtonNumber = getThirdButtonNumber();
                break;
            case 4:
                displayedButtonNumber = getFourthButtonNumber();
                break;
            case 5:
                displayedButtonNumber = getFifthButtonNumber();
                break;
            default:
                displayedButtonNumber = 0;
                break;
        }

        return displayedButtonNumber == currentPage;
    }

    /**
     * @return index of the first item in the filtered database, that should be
     * displayed on row 0 in the visible table.
     */
    protected int getStartIndex() {
        int startIndex = (itemsPerPage * currentPage) - itemsPerPage;
        return startIndex < 0 ? 0 : startIndex;
    }

    /**
     * @return index of the last item in the filtered database, that should be
     * displayed on row 0 in the visible table.
     */
    protected int getEndIndex() {
        return (itemsPerPage * currentPage) - 1;
    }

    /**
     * Set the size of the filtered database.
     *
     * @param totalSize
     */
    protected void setDataListTotalSize(int totalSize) {
        dataListTotalSize = totalSize;
    }

    public int getDataListTotalSize() {
        return dataListTotalSize;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }
}
