package com.dm4nk.clients.book;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class UnlinkBookRequest {
    UUID bookId;
}
