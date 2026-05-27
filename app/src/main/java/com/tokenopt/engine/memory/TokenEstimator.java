package com.tokenopt.engine.memory;

public class TokenEstimator {
    private TokenEstimator(){}

    public static long cumulativeNaiveTokens(int turns, int avgTokensPerTurn){
        if(turns <=0 || avgTokensPerTurn <=0){
            return 0;
        }
        return (long) avgTokensPerTurn * turns *(turns +1L) / 2L;
    }

    public static long cumulativeRagTokens(int turns, int avgTokensPerTurn, int k){
        if(turns <=0 || avgTokensPerTurn <=0){
            return 0L;
        }
        int effectiveK = Math.max(0,k);
        long perTurn = (long) (effectiveK + 1) * avgTokensPerTurn;
        return perTurn * turns;
    }
    public static double savingsPercentage(long naive, long rag){
        if(naive <=0){
            return 0.0;
        }
        return 100.0 * (naive -rag) / naive;
    }
}
