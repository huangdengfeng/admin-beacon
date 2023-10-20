package com.seezoon.infrastructure.dto;

import java.util.List;

/**
 * use for response with page
 *
 * @param <T>
 */
public class Page<T> {

    private long total;
    private List<T> data;

    public Page() {
    }

    public Page(List<T> list) {
        this.data = list;
    }

    public Page(long total, List<T> list) {
        this.total = total;
        this.data = list;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> list) {
        this.data = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

}
