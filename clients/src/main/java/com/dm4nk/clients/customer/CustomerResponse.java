package com.dm4nk.clients.customer;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CustomerResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private OffsetDateTime createdAt;
    private Boolean isBanned;
    private List<NotePreviewResponse> notes;
}
