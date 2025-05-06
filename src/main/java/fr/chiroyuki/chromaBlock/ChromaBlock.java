package fr.chiroyuki.chromaBlock;

import fr.chiroyuki.chromaBlock.commands.ChromaGradientCommand;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.io.File;
import java.util.Map;

public final class ChromaBlock extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("chroma").setExecutor(new ChromaGradientCommand());

        saveDefaultConfig();
        File chartsDir = new File(getDataFolder(), "charts");
        if (!chartsDir.exists()) chartsDir.mkdirs();

        String chartFile = getConfig().getString("chart-file", "wool.yml");
        Map<Material, Color> chart = ChartLoader.loadColorChart(getDataFolder(), chartFile);

        BlockMatcher.setColorChart(chart);

        getLogger().info("ChromaBlock chargé avec la chart : " + chartFile);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
