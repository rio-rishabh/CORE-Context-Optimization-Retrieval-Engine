package com.tokenopt.engine.memory;

import java.util.List;
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
        float[] queryVector = embeddingService.embed(userQuestion);
        List<MemoryChunk> memories = memoryStore.searchTopK(conversationID, queryVector, topK);
        return formatMemories(memories);
    }

    public record ChatContext(String systemContext, List<MemoryChunk> retrieveMemories){}
    public ChatContext buildContextWithMemories(String conversationId, String userQuestion){
        float[] queryVector = embeddingService.embed(userQuestion);
        List<MemoryChunk> memories = memoryStore.searchTopK(conversationId, queryVector, topK);
        return new ChatContext(formatMemories(memories), memories);
    }

    private String formatMemories(List<MemoryChunk> memories){
        if(memories == null || memories.isEmpty()){
            return "";
        }
        StringBuilder context = new StringBuilder("Relevant context from earlier in the conversation: \n");
        for(MemoryChunk memory : memories){
            context.append(" -[").append(memory.role()).append("]")
            .append(memory.text()).append("\n");
        }
        return context.toString();
    }
}
