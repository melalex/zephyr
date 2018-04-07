package com.zephyr.commons.support;

import lombok.Value;

@Value(staticConstructor = "of")
public class Indexed<T> {

    private int index;
    private T element;
}
