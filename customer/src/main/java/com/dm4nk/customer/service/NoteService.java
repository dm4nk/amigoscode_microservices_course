package com.dm4nk.customer.service;

import com.dm4nk.aop.logger.Level;
import com.dm4nk.aop.logger.annotations.Loggable;
import com.dm4nk.clients.note.NoteClient;
import com.dm4nk.clients.note.NoteRequest;
import com.dm4nk.clients.note.NoteResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Loggable(level = Level.INFO)
@AllArgsConstructor
public class NoteService {
    private final NoteClient noteClient;

    public NoteResponse getNote(UUID noteId) {
        return noteClient.getNote(noteId);
    }

    public NoteResponse updateNote(NoteRequest noteRequest) {
        return noteClient.updateNote(noteRequest);
    }
}
