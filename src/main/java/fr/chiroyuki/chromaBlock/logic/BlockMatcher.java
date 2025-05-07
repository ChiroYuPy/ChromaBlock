package fr.chiroyuki.chromaBlock.logic;

import org.bukkit.Material;

import java.awt.Color;
import java.util.*;

public class BlockMatcher {

    private static final Map<Material, Color> blockColors = new HashMap<>();

    public static void setColorChart(Map<Material, Color> chart) {
        blockColors.clear();
        blockColors.putAll(chart);
    }

    public static Material getClosestMaterial(Color target) {
        return blockColors.entrySet().stream()
                .min((a, b) -> Double.compare(colorDistance(target, a.getValue()), colorDistance(target, b.getValue())))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private static double colorDistance(Color c1, Color c2) {
        int dr = c1.getRed() - c2.getRed();
        int dg = c1.getGreen() - c2.getGreen();
        int db = c1.getBlue() - c2.getBlue();
        return dr * dr + dg * dg + db * db;
    }
}