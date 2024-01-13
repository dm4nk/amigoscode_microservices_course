package com.dm4nk.ban.exceptions;

import com.dm4nk.ban.common.Constants;
import com.google.common.collect.ImmutableMap;

import static com.dm4nk.ban.common.Constants.ExceptionMessages.TOO_MANY_VALUES;
import static com.dm4nk.ban.common.Constants.ExceptionReason.EXTRA_FIELD_SPECIFIED;

public class BanExceptionFactory {
    private static final ImmutableMap<Constants.ExceptionReason, BanException> REASON_TO_EXCEPTION = ImmutableMap.of(
            EXTRA_FIELD_SPECIFIED, new BanException(TOO_MANY_VALUES)
    );

    public static BanException generate(Constants.ExceptionReason exceptionReason) {
        return REASON_TO_EXCEPTION.get(exceptionReason);
    }
}
