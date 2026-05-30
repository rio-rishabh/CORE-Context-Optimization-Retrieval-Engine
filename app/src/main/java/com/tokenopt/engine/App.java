package com.tokenopt.engine;

import com.tokenopt.engine.memory.HashEmbeddingService;
import com.tokenopt.engine.memory.InMemoryMemoryStore;
import com.tokenopt.engine.memory.ChatOrchestrator;
import com.tokenopt.engine.memory.ChatOrchestrator.ChatContext;
import com.tokenopt.engine.memory.TokenEstimator;

public class App {
    public String getGreeting() {
        return "CORE Context Optimization Retrieval Engine";
    }

    public static void main(String[] args) {
        var embedder = new HashEmbeddingService(256);
        var store = new InMemoryMemoryStore();
        var orchestrator = new ChatOrchestrator(embedder, store, 5);

        String conversation = "demo-conversation";
        // *Needle in HayStack Demo*

        orchestrator.handleTurn(conversation, "The primary API key is sk-alpha-123", "Noted. I'll remember this key" );

        for(int i = 0 ; i< 45; i++){ // its it to find needle in the haystack since If a user asks questions then there must be reply to related topic.
            orchestrator.handleTurn(conversation, "unrelated topic about css architecture turn "+ i, "acknowledged turn" +i);
        }

        String question = "what is the primary api key?";
        ChatContext context = orchestrator.buildContextWithMemories(conversation, question );
        System.out.println("Question: " + question);
        System.out.println();
        System.out.println("retrieved memories: (" + context.retrieveMemories().size() +"):");
        context.retrieveMemories().forEach(m -> System.out.println(" - [" + m.role() +"]" + m.text()));
        System.out.println();
        System.out.println(context.systemContext());
        boolean foundNeedle = context.systemContext().contains("sk-alpha-123");
        System.out.println();
        System.out.println("Needle found in context: " + foundNeedle);
        // --- Token efficiency demo ---
        System.out.println();
        System.out.println("=== Token efficiency (50 turns, avg 100 tokens/turn, K=5) ===");
        int turns = 50;
        int avgTokens = 100;
        int k = 5;
        long naive = TokenEstimator.cumulativeNaiveTokens(turns, avgTokens);
        long rag = TokenEstimator.cumulativeRagTokens(turns, avgTokens, k);
        double savings = TokenEstimator.savingsPercentage(naive, rag);
        System.out.printf("Naive cumulative tokens: %,d%n", naive);
        System.out.printf("RAG cumulative tokens:   %,d%n", rag);
        System.out.printf("Tokens saved:            %.1f%%%n", savings);
    }
}
