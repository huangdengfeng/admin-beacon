package com.seezoon.infrastructure.dto;

import java.util.List;

/**
 * use for response with page
 *
 * @param <T>
 */
public class Page<T> {

    private long total;
    private List<T> list;

    public Page() {
    }

    public Page(List<T> list) {
        this.list = list;
    }

    public Page(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public List<T> getData() {
        return list;
    }

    public void setData(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

}
