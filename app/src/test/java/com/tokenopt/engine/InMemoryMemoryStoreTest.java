package com.tokenopt.engine;

import com.tokenopt.engine.memory.InMemoryMemoryStore;
import com.tokenopt.engine.memory.MemoryChunk;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryMemoryStoreTest {

    private final InMemoryMemoryStore store = new InMemoryMemoryStore();

    private MemoryChunk chunk(String conversationId, String text, float[] embedding) {
        return new MemoryChunk(
                UUID.randomUUID().toString(),
                conversationId,
                "user",
                text,
                embedding,
                Instant.now()
        );
    }

    @Test
    void searchTopK_returnsClosestMatchFirst() {
        String conv = "conv-1";
        store.save(chunk(conv, "auth notes", new float[]{1f, 0f, 0f}));
        store.save(chunk(conv, "db notes", new float[]{0f, 1f, 0f}));
        store.save(chunk(conv, "ui notes", new float[]{0f, 0f, 1f}));

        List<MemoryChunk> top = store.searchTopK(conv, new float[]{0f, 1f, 0f}, 2);

        assertEquals(2, top.size());
        assertEquals("db notes", top.get(0).text());
    }

    @Test
    void searchTopK_respectsKLimit() {
        String conv = "conv-1";
        store.save(chunk(conv, "one", new float[]{1f, 0f, 0f}));
        store.save(chunk(conv, "two", new float[]{0f, 1f, 0f}));
        store.save(chunk(conv, "three", new float[]{0f, 0f, 1f}));

        List<MemoryChunk> top = store.searchTopK(conv, new float[]{1f, 0f, 0f}, 1);

        assertEquals(1, top.size());
    }

    @Test
    void searchTopK_unknownConversation_returnsEmpty() {
        List<MemoryChunk> top = store.searchTopK("missing", new float[]{1f, 0f, 0f}, 5);

        assertTrue(top.isEmpty());
    }

    @Test
    void searchTopK_kIsZero_returnsEmpty() {
        String conv = "conv-1";
        store.save(chunk(conv, "one", new float[]{1f, 0f, 0f}));

        List<MemoryChunk> top = store.searchTopK(conv, new float[]{1f, 0f, 0f}, 0);

        assertTrue(top.isEmpty());
    }

    @Test
    void deleteConversation_clearsResults() {
        String conv = "conv-1";
        store.save(chunk(conv, "one", new float[]{1f, 0f, 0f}));
        store.save(chunk(conv, "two", new float[]{0f, 1f, 0f}));

        store.deleteConversation(conv);

        List<MemoryChunk> top = store.searchTopK(conv, new float[]{1f, 0f, 0f}, 5);
        assertTrue(top.isEmpty());
    }

    @Test
    void save_scopesByConversationId() {
        store.save(chunk("conv-A", "secret A", new float[]{1f, 0f, 0f}));
        store.save(chunk("conv-B", "secret B", new float[]{0f, 1f, 0f}));

        List<MemoryChunk> top = store.searchTopK("conv-A", new float[]{0f, 1f, 0f}, 5);

        assertEquals(1, top.size());
        assertEquals("secret A", top.get(0).text());
    }
}
