package com.dm4nk.clients.search;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FullTextSearchRequest {
    String text;
}
