package com.tokenopt.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.tokenopt.engine.memory.HashEmbeddingService;
import com.tokenopt.engine.memory.InMemoryMemoryStore;
import com.tokenopt.engine.memory.MemoryStore;
import com.tokenopt.engine.memory.ChatOrchestrator;
import com.tokenopt.engine.memory.ChatOrchestrator.ChatContext;
import com.tokenopt.engine.memory.EmbeddingService;

public class NeedleInHaystackTest {
    @Test
    void retrievesApiKeyAfterManyUnrelatedTurns() {
        EmbeddingService embedder = new HashEmbeddingService(256);
        MemoryStore store = new InMemoryMemoryStore();
        ChatOrchestrator orchestrator = new ChatOrchestrator(embedder, store, 5);
        String conv = "conv-needle";
        // Needle (early in conversation)
        orchestrator.handleTurn(conv,
                "The primary API key is sk-alpha-123.",
                "Noted. I will remember this key.");
        // Haystack — 45 unrelated turns (22 user + 22 assistant chunks after needle)
        for (int i = 0; i < 45; i++) {
            orchestrator.handleTurn(conv,
                    "Unrelated topic about CSS architecture turn " + i,
                    "Acknowledged turn " + i);
        }
        // Turn-50-style question
        ChatContext ctx = orchestrator.buildContextWithMemories(conv,
                "What is the primary API key?");
        assertTrue(ctx.retrieveMemories().stream()
                        .anyMatch(m -> m.text().contains("sk-alpha-123")),
                "Top-K memories should include the needle chunk");
        assertTrue(ctx.systemContext().contains("sk-alpha-123"),
                "Formatted context should include the needle");
    }
}
