package fr.chiroyuki.chromaBlock.data;

import fr.chiroyuki.chromaBlock.logic.ColorInterpolator;

import java.awt.Color;
import java.util.List;

public class Gradient {
    private final Color startColor;
    private final Color endColor;
    private final int numPoints;
    private final ColorInterpolator.Easing easingFunction;

    public Gradient(Color startColor, Color endColor, int numPoints, ColorInterpolator.Easing easingFunction) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.numPoints = numPoints;
        this.easingFunction = easingFunction;
    }

    public Color getStartColor() {
        return startColor;
    }

    public Color getEndColor() {
        return endColor;
    }

    public int getNumPoints() {
        return numPoints;
    }

    public ColorInterpolator.Easing getEasingFunction() {
        return easingFunction;
    }

    public List<Color> getInterpolatedColors() {
        return ColorInterpolator.interpolateColors(startColor, endColor, numPoints, easingFunction);
    }

    public List<Color> generateColors() {
        return ColorInterpolator.interpolateColors(startColor, endColor, numPoints, easingFunction);
    }
}
