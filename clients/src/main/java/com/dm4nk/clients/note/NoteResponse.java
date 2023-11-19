package com.dm4nk.clients.note;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class NoteResponse {
    private UUID id;
    private String caption;
    private String text;
}
