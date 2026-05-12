package com.tokenopt.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.tokenopt.engine.memory.VectorMath;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VectorMathTest {

    @Test
    public void cosineSimilarity_identicalVectors_returnsOne() {
        float[] vector1 = {1.0f, 2.0f, 3.0f};
        float[] vector2 = {1.0f, 2.0f, 3.0f};
        float result = VectorMath.cosineSimilarity(vector1, vector2);
        assertEquals(1.0f, result, 1e-6);
    }

    @Test
    public void cosineSimilarity_OrthogonalVectors_returnsZero() {
        float[] vector1 = {1.0f, 0.0f, 0.0f};
        float[] vector2 = {0.0f, 1.0f, 0.0f};
        float result = VectorMath.cosineSimilarity(vector1, vector2);
        assertEquals(0.0f, result, 1e-6);
    }

    @Test
    public void cosineSimilarity_OppositeVectors_returnsMinusOne() {
        float[] vector1 = {1.0f, 0.0f, 0.0f};
        float[] vector2 = {-1.0f, 0.0f, 0.0f};
        float result = VectorMath.cosineSimilarity(vector1, vector2);
        assertEquals(-1.0f, result, 1e-6);
    }

    @Test
    public void cosineSimilarity_ZeroVectors_returnsZero() {
        float[] vector1 = {0.0f, 0.0f, 0.0f};
        float[] vector2 = {1.0f, 2.0f, 3.0f};
        float result = VectorMath.cosineSimilarity(vector1, vector2);
        assertEquals(0.0f, result, 1e-6);
    }
    @Test
    public void cosineSimilarity_MismatchedDimensions_throwsException() {
        float[] vector1 = {1.0f, 2.0f, 3.0f};
        float[] vector2 = {1.0f, 2.0f};
        assertThrows(IllegalArgumentException.class, () -> VectorMath.cosineSimilarity(vector1, vector2));
    }
}
