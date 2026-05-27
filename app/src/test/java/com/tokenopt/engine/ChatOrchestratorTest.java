package com.tokenopt.engine;

import com.tokenopt.engine.memory.HashEmbeddingService;
import com.tokenopt.engine.memory.InMemoryMemoryStore;
import com.tokenopt.engine.memory.EmbeddingService;
import com.tokenopt.engine.memory.MemoryStore;
import com.tokenopt.engine.memory.ChatOrchestrator;
import com.tokenopt.engine.memory.ChatOrchestrator.ChatContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ChatOrchestratorTest {

    EmbeddingService embedder = new HashEmbeddingService(256);
    MemoryStore store = new InMemoryMemoryStore();
    ChatOrchestrator orchestrator = new ChatOrchestrator(embedder, store, 10);

    @Test
    void buildContext_retrievesEarlierFact(){
        String conversationId = "conv-1";
        
        orchestrator.handleTurn(conversationId, "What is the capital of France?", "Paris");

        for(int i =0; i< 10; i++){
            orchestrator.handleTurn(conversationId, "Unrelated topic about CSS turn"+ i, "acknowledged");
        }

        ChatContext ctx = orchestrator.buildContextWithMemories(conversationId, "what is the capital of France?");
        assertTrue(ctx.retrieveMemories().stream().anyMatch(m -> m.text().contains("What is the Capitol")));
        assertFalse(ctx.systemContext().contains("Corner"));
    }
}
