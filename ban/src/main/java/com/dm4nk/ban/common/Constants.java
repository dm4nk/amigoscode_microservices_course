package com.dm4nk.ban.common;

public interface Constants {
    enum ExceptionReason {
        EXTRA_FIELD_SPECIFIED
    }

    interface DB {
        String BANS = "bans";
        String ID = "id";
        String CUSTOMER_ID = "customer_id";
        String VALID_FROM = "valid_from";
        String VALID_TO = "valid_to";
    }

    interface Web {
        String API = "api/";
        String V1 = "v1/";
        String BAN = "ban/";
        String BAN_CHECK = "ban-check/";
    }

    interface ExceptionMessages {
        String TOO_MANY_VALUES = "You have specified either duration of ban, and date of ban finishing. " +
                "Please, leave only one value specified";
    }
}
