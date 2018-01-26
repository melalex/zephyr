package com.zephyr.errors.dsl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zephyr.errors.domain.Path;
import com.zephyr.errors.utils.ErrorUtil;

import java.util.List;

public final class PathSpec<T> {
    private static final String ROOT_ASSERTION_MESSAGE = "root can't be null";

    private final AssembleCallback<T, Path> callback;

    private String root;
    private final List<String> path = Lists.newLinkedList();

    PathSpec(AssembleCallback<T, Path> callback) {
        this.callback = callback;
    }

    public PathSpec<T> root(String root) {
        this.root = root;
        return this;
    }

    public PathSpec<T> with(String pathPart) {
        path.add(pathPart);
        return this;
    }

    public PathSpec<T> with(Enum<?> value) {
        return with(ErrorUtil.identifier(value));
    }

    public T completePath() {
        Preconditions.checkNotNull(root, ROOT_ASSERTION_MESSAGE);

        return callback.onAssemble(Path.of(root).to(path));
    }
}
