package com.zephyr.errors.converters.internal;

import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
public class MessageSourceHolder {

    @Setter(onMethod = @__(@Autowired(required = false)))
    private MessageSource messageSource;

    public String getMessage(@NonNull String code, @Nullable Object[] args, @NonNull Locale locale) {
        return Optional.ofNullable(messageSource).map(m -> m.getMessage(code, args, code, locale))
                .orElse(code);
    }

    public String getMessage(@NonNull String code, @NonNull Locale locale) {
        return getMessage(code, null, locale);
    }
}