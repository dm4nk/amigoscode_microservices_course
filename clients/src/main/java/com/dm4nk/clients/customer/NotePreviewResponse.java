package com.dm4nk.clients.customer;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class NotePreviewResponse {
    private UUID id;
    private String caption;
}
