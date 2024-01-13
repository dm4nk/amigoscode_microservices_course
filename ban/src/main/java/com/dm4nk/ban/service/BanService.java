package com.dm4nk.ban.service;

import com.dm4nk.aop.logger.Level;
import com.dm4nk.aop.logger.annotations.Loggable;
import com.dm4nk.ban.db.model.jooq.generated.tables.records.BansRecord;
import com.dm4nk.ban.exceptions.BanExceptionFactory;
import com.dm4nk.clients.ban.BanRequest;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.dm4nk.ban.common.Constants.ExceptionReason.EXTRA_FIELD_SPECIFIED;
import static com.dm4nk.ban.db.model.jooq.generated.tables.Bans.BANS;

@Service
@Loggable(level = Level.INFO)
@AllArgsConstructor
public class BanService {

    private final DSLContext context;

    public Boolean isBannedCustomer(UUID customerId) {
        return context.fetchExists(
                context.selectOne()
                        .from(BANS)
                        .where(BANS.CUSTOMER_ID.eq(customerId))
                        .and(BANS.VALID_TO.greaterThan(OffsetDateTime.now()))
        );
    }

    public UUID ban(BanRequest banRequest) {
        UUID customerId = banRequest.getCustomerId();

        Integer duration = banRequest.getDuration();
        OffsetDateTime validTo = banRequest.getValidTo();

        if (duration == null && validTo != null) {
            return banUntil(customerId, validTo);
        }
        if (duration != null && validTo == null) {
            return banForDays(customerId, duration);
        }

        throw BanExceptionFactory.generate(EXTRA_FIELD_SPECIFIED);
    }

    private UUID banUntil(UUID customerId, OffsetDateTime validTo) {
        BansRecord bansRecord = context.newRecord(BANS);
        bansRecord.setCustomerId(customerId);
        bansRecord.setValidTo(validTo);
        bansRecord.setValidFrom(OffsetDateTime.now());
        bansRecord.store();
        return customerId;
    }

    private UUID banForDays(UUID customerId, Integer duration) {
        return banUntil(customerId, OffsetDateTime.now().plusDays(duration));
    }
}
