package com.zephyr.errors.domain;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.zephyr.errors.utils.ErrorUtil;
import lombok.Value;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Value
public class Path implements Serializable {
    private static final long serialVersionUID = -1335468596895051110L;

    private String root;
    private List<String> path = Lists.newLinkedList();

    public List<String> getPathParts() {
        return ImmutableList.copyOf(path);
    }

    public static Path of(String root) {
        return new Path(root);
    }

    public static Path of(Enum<?> value) {
        return of(ErrorUtil.identifier(value));
    }

    public Path to(String pathPart) {
        path.add(pathPart);
        return this;
    }

    public Path to(Enum<?> pathPart) {
        path.add(ErrorUtil.identifier(pathPart));
        return this;
    }

    public Path to(Collection<String> pathPart) {
        path.addAll(pathPart);
        return this;
    }

    public List<String> getPath() {
        return ImmutableList.copyOf(path);
    }

    public List<String> getFullPath() {
        List<String> fullPath = Lists.newArrayList(root);
        fullPath.addAll(path);

        return fullPath;
    }

    public String getFullPathCode() {
        return Joiner.on(ErrorUtil.ERROR_CODE_SEPARATOR)
                .skipNulls()
                .join(getFullPath());
    }

    public String getPathCode() {
        return Joiner.on(ErrorUtil.ERROR_CODE_SEPARATOR)
                .skipNulls()
                .join(path);
    }
}
