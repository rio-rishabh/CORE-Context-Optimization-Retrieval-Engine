package com.tokenopt.engine.memory;

public final class VectorMath {
    private VectorMath() {
    }

    public static float[] normalize(float[] vector) {
        double sumSquares = 0.0;
        for (float value : vector) {
            sumSquares += value * value;
        }
        if (sumSquares == 0.0) {
            return vector;
        }
        double magnitude = Math.sqrt(sumSquares);
        float[] normalized = new float[vector.length];
        for (int i = 0; i < vector.length; i++) {
            normalized[i] = (float) (vector[i] / magnitude);
        }
        return normalized;
    }
}
