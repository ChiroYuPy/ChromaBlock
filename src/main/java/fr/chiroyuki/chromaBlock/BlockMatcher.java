package fr.chiroyuki.chromaBlock;

import org.bukkit.Material;

import java.awt.Color;
import java.util.*;

public class BlockMatcher {

    private static Map<Material, Color> blockColors = new HashMap<>();

    public static void setColorChart(Map<Material, Color> chart) {
        blockColors = chart;
    }

    public static Material getClosestMaterial(Color target) {
        Material closest = null;
        double minDistance = Double.MAX_VALUE;

        for (Map.Entry<Material, Color> entry : blockColors.entrySet()) {
            double distance = colorDistance(target, entry.getValue());
            if (distance < minDistance) {
                minDistance = distance;
                closest = entry.getKey();
            }
        }

        return closest;
    }

    private static double colorDistance(Color c1, Color c2) {
        int dr = c1.getRed() - c2.getRed();
        int dg = c1.getGreen() - c2.getGreen();
        int db = c1.getBlue() - c2.getBlue();
        return dr * dr + dg * dg + db * db;
    }
}
