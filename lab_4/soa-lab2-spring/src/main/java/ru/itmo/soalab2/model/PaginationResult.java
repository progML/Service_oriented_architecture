package ru.itmo.soalab2.model;

import java.util.ArrayList;
import java.util.List;

public class PaginationResult {

    private final int pageSize;
    private final int pageIndex;
    private final long totalItems;
    private final List<HumanBeing> list;
    PaginationResult() {
        pageSize = 0;
        pageIndex = 0;
        totalItems = 0;
        list = new ArrayList<>();
    }

    public PaginationResult(int pageSize, int pageIndex, long totalItems, List<HumanBeing> list) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.totalItems = totalItems;
        this.list = list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public List<HumanBeing> getList() {
        return list;
    }
}
