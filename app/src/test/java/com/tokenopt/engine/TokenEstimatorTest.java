package com.tokenopt.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.tokenopt.engine.memory.TokenEstimator;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenEstimatorTest {

    @Test
    void cumulativeNaiveTokens_oneTurn(){
        assertEquals(100L, TokenEstimator.cumulativeNaiveTokens(1, 100));
    }

    @Test
    void cumulativeNaiveTokens_multipleTurns(){
        assertEquals(127_500L, TokenEstimator.cumulativeNaiveTokens(50, 100));
    }
    @Test
    void cumulativeRagTokens_fiftyTurns_k5(){
        assertEquals(30_000L, TokenEstimator.cumulativeRagTokens(50, 100, 5));
    }

    @Test
    void savingsPercent_fiftyTurns_k5() {
        long naive = TokenEstimator.cumulativeNaiveTokens(50, 100);
        long rag = TokenEstimator.cumulativeRagTokens(50, 100, 5);
        double savings = TokenEstimator.savingsPercentage(naive, rag);
        assertTrue(savings > 70.0); // ~76.5%
        assertEquals(76.47, savings, 0.1);
    }
    @Test
    void zeroTurns_returnsZero() {
        assertEquals(0L, TokenEstimator.cumulativeNaiveTokens(0, 100));
        assertEquals(0L, TokenEstimator.cumulativeRagTokens(0, 100, 5));
    }
    @Test
    void savingsPercent_whenNaiveZero_returnsZero() {
        assertEquals(0.0, TokenEstimator.savingsPercentage(0, 0));
    }
}
