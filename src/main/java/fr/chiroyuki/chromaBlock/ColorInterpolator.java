package fr.chiroyuki.chromaBlock;

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
        List<Color> result = new ArrayList<>();

        for (int i = 0; i < steps; i++) {
            double t = (double) i / (steps - 1);
            t = easing.apply(t);

            int r = (int) (start.getRed() + t * (end.getRed() - start.getRed()));
            int g = (int) (start.getGreen() + t * (end.getGreen() - start.getGreen()));
            int b = (int) (start.getBlue() + t * (end.getBlue() - start.getBlue()));

            result.add(new Color(r, g, b));
        }

        return result;
    }
}
