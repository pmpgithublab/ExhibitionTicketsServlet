package ua.training.model.dto;

import java.util.ArrayList;
import java.util.List;

public class ReportDTO<T> {
    private List<T> items = new ArrayList<>();
    private int pageQuantity;
    private int currentPage;
    private String pageNavigationString;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPageQuantity() {
        return pageQuantity;
    }

    public void setPageQuantity(int pageQuantity) {
        this.pageQuantity = pageQuantity;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getPageNavigationString() {
        return pageNavigationString;
    }

    public void setPageNavigationString(String pageNavigationString) {
        this.pageNavigationString = pageNavigationString;
    }
}
