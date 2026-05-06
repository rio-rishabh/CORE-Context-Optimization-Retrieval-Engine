package com.tokenopt.engine.memory;

import java.time.Instant;

public record MemoryChunk(
        String id,
        String conversationId,
        String role,
        String text,
        float[] embedding,
        Instant createdAt
) {
}
