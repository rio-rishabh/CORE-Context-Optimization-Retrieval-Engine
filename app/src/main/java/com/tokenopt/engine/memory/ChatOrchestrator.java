package com.tokenopt.engine.memory;

import java.util.UUID;
import java.time.Instant;

public class ChatOrchestrator {
    private final MemoryStore memoryStore;
    private final EmbeddingService embeddingService;
    private final int topK;

    public ChatOrchestrator(EmbeddingService embeddingService, MemoryStore memoryStore, int topK) {
        this.embeddingService = embeddingService;
        this.memoryStore = memoryStore;
        this.topK = topK;
    }

    public void handleTurn(String conversationId, String userText, String assistantText){
        saveChunk(conversationId, "user", userText);
        saveChunk(conversationId, "assistant", assistantText);
    }

    public void saveChunk(String conversationId, String role, String text){
        float[] embedding = embeddingService.embed(text);
        MemoryChunk chunk = new MemoryChunk(UUID.randomUUID().toString(),conversationId, role, text, embedding, Instant.now());
        memoryStore.save(chunk);
    }
    public String buildContext(String conversationID, String userQuestion){
        float[] queryVector = embeddingService.embed(userQuestion)
    }
}
