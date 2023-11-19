package com.dm4nk.clients.note;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(
        name = "note",
        url = "${clients.note.url}"
)
public interface NoteClient {
    @GetMapping(path = "/api/v1/note/{noteId}")
    NoteResponse getNote(@PathVariable("noteId") UUID noteId);

    @PostMapping(path = "/api/v1/note")
    NoteResponse updateNote(@RequestBody NoteRequest noteRequest);
}
