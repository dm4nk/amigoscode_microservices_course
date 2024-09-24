package com.dm4nk.customer.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@MappedSuperclass
public class BaseEntity {
    @Getter
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false)
    private UUID id;

    @EqualsAndHashCode.Include
    @Getter
    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    @Getter
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdDate;

    @Getter
    @Column(name = "last_modified_date", nullable = false)
    @UpdateTimestamp
    private Instant lastModifiedDate;
}
