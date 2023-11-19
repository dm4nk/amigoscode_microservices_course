package com.dm4nk.clients.note;

import java.util.UUID;

public record NoteRequest(
        UUID id,
        String caption,
        String text) {
}
