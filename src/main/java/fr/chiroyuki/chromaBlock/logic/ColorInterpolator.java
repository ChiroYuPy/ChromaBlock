package fr.chiroyuki.chromaBlock.logic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class ColorInterpolator {

    public enum Easing {
        LINEAR(t -> t),
        EASE_IN(t -> t * t),
        EASE_OUT(t -> 1 - Math.pow(1 - t, 2)),
        EASE_IN_OUT(t -> t < 0.5 ? 2 * t * t : 1 - Math.pow(-2 * t + 2, 2) / 2);

        private final DoubleUnaryOperator function;

        Easing(DoubleUnaryOperator function) {
            this.function = function;
        }

        public double apply(double t) {
            return function.applyAsDouble(t);
        }
    }

    public static List<Color> interpolateColors(Color start, Color end, int steps, Easing easing) {
        if (steps < 2) {
            throw new IllegalArgumentException("Le nombre d'étapes doit être supérieur ou égal à 2.");
        }

        List<Color> result = new ArrayList<>();

        for (int i = 0; i < steps; i++) {
            float t = (float) i / (steps - 1);
            t = (float) easing.apply(t);

            Color color = interpolateLinear(start, end, t);
            result.add(color);
        }

        return result;
    }

    private static Color interpolateLinear(Color startColor, Color endColor, float ratio) {
        int r = clamp((int) (startColor.getRed() + (endColor.getRed() - startColor.getRed()) * ratio), 0, 255);
        int g = clamp((int) (startColor.getGreen() + (endColor.getGreen() - startColor.getGreen()) * ratio), 0, 255);
        int b = clamp((int) (startColor.getBlue() + (endColor.getBlue() - startColor.getBlue()) * ratio), 0, 255);
        return new Color(r, g, b);
    }

    private static int clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }
}
