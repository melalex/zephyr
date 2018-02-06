package com.zephyr.commons.interfaces;

@FunctionalInterface
public interface Matcher<E, T> {

    boolean matches(E example, T target);
}
