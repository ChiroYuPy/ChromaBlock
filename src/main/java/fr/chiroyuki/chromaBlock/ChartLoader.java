package fr.chiroyuki.chromaBlock;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChartLoader {

    public static Map<Material, Color> loadColorChart(File dataFolder, String chartFileName) {
        File chartFile = new File(new File(dataFolder, "charts"), chartFileName);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(chartFile);
        Map<Material, Color> colorMap = new HashMap<>();

        for (String key : config.getKeys(false)) {
            try {
                Material material = Material.valueOf(key);
                Color color = Color.decode(Objects.requireNonNull(config.getString(key)));
                colorMap.put(material, color);
            } catch (Exception e) {
                System.out.println("[ChromaBlock] Erreur dans le fichier chart : " + key + " -> ignoré.");
            }
        }

        return colorMap;
    }
}
