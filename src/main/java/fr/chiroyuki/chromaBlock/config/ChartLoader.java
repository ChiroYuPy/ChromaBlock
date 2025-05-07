package fr.chiroyuki.chromaBlock.config;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ChartLoader {

    public static Map<Material, Color> load(File file) {
        Map<Material, Color> chart = new HashMap<>();

        if (!file.exists()) {
            throw new IllegalArgumentException("Fichier de chart introuvable : " + file.getPath());
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (String key : config.getKeys(false)) {
            try {
                Material material = Material.valueOf(key.toUpperCase());
                String hex = config.getString(key);
                Color color = Color.decode(hex);
                chart.put(material, color);
            } catch (IllegalArgumentException | NullPointerException e) {
                System.err.println("[ChromaBlock] Erreur: '" + key + "' n'est pas un Material valide ou une couleur invalide.");
            }
        }

        return chart;
    }
}
