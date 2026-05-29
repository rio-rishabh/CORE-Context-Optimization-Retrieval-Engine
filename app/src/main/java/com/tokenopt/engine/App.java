package com.tokenopt.engine;

import com.tokenopt.engine.memory.HashEmbeddingService;
import com.tokenopt.engine.memory.InMemoryMemoryStore;
import com.tokenopt.engine.memory.ChatOrchestrator;

public class App {
    public String getGreeting() {
        return "CORE Context Optimization Retrieval Engine";
    }

    public static void main(String[] args) {
        HashEmbeddingService embeddingService = new HashEmbeddingService(256);
        float[] sample = embeddingService.embed("Semantic memory retrieval for long-running chat.");
        System.out.println(new App().getGreeting());
        System.out.println("Sample embedding dimensions: " + sample.length);


        var embedder = new HashEmbeddingService(256);
        var store = new InMemoryMemoryStore();
        var orchestrator = new ChatOrchestrator(embedder, store, 5);

        String conversation = "demo-conversation";
        // *Needle in HayStack Demo*

        orchestrator.handleTurn(conversation, "The primary API key is sk-alpha-123", "Noted. I'll remember this key" );
    }
}
