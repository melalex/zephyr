package com.zephyr.commons.support;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Builder
public class Page {

    @Wither
    private int page;
    private int first;
    private int pageSize;
    private int count;

    public int getPageSize() {
        return isNotLast() ? pageSize : count - getStart() + first;
    }

    public int getStart() {
        return first + page * pageSize;
    }

    public int getLastPage() {
        return (int) Math.ceil(count * 1.0 / pageSize) - 1;
    }

    public boolean isFirst() {
        return page == 0;
    }

    public boolean isNotFirst() {
        return !isFirst();
    }

    public boolean isLast() {
        return getLastPage() == page;
    }

    public boolean isNotLast() {
        return !isLast();
    }

    public Page getNext() {
        return withPage(page + 1);
    }
}