package com.dm4nk.ban.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.dm4nk.ban.common.Constants.DB.BANS;
import static com.dm4nk.ban.common.Constants.DB.CUSTOMER_ID;
import static com.dm4nk.ban.common.Constants.DB.ID;
import static com.dm4nk.ban.common.Constants.DB.VALID_FROM;
import static com.dm4nk.ban.common.Constants.DB.VALID_TO;

@Entity
@Table(name = BANS)
public class BanCheckHistory {
    @Id
    @Column(name = ID)
    private UUID id;
    @Column(name = CUSTOMER_ID)
    private UUID customerId;
    @Column(name = VALID_FROM)
    private LocalDateTime validFrom;
    @Column(name = VALID_TO)
    private LocalDateTime validTo;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }
}
