package com.zephyr.test.mocks;

import static org.mockito.Mockito.when;

import com.zephyr.commons.ListUtils;
import com.zephyr.commons.interfaces.UidProvider;
import lombok.experimental.UtilityClass;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

@UtilityClass
public class UidProviderMock {

    public static final String DEFAULT_ID = UUID.randomUUID().toString();

    // TODO: Java 10 migration
    public static UidProvider of(List<String> ids) {
        var mock = Mockito.mock(UidProvider.class);
        var args = ListUtils.toSafeVarArg(ids, String.class);
        when(mock.provide()).thenReturn(args.getFirst(), args.getRest());
        return mock;
    }

    public static UidProvider of(String id) {
        return of(List.of(id));
    }

    public static UidProvider of() {
        return of(DEFAULT_ID);
    }
}
