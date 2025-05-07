package fr.chiroyuki.chromaBlock;

import fr.chiroyuki.chromaBlock.commands.ChromaGradientCommand;
import fr.chiroyuki.chromaBlock.config.ChartLoader;
import fr.chiroyuki.chromaBlock.logic.BlockMatcher;
import fr.chiroyuki.chromaBlock.managers.GradientManager;
import fr.chiroyuki.chromaBlock.commands.MessageUtils;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.io.File;
import java.util.Map;
import java.util.Objects;

public final class ChromaBlock extends JavaPlugin {

    private GradientManager gradientManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        MessageUtils.init(this);

        this.gradientManager = new GradientManager();

        loadColorChart();

        ChromaGradientCommand commandHandler = new ChromaGradientCommand(this);
        Objects.requireNonNull(getCommand("chroma")).setExecutor(commandHandler);
        Objects.requireNonNull(getCommand("chroma")).setTabCompleter(commandHandler);

        getLogger().info("ChromaBlock activé.");
    }

    private void loadColorChart() {
        String chartFileName = getConfig().getString("chart", "wool.yml");
        File chartFile = new File(getDataFolder(), "charts/" + chartFileName);

        try {
            Map<Material, Color> chart = ChartLoader.load(chartFile);
            BlockMatcher.setColorChart(chart);
            getLogger().info("Color chart chargée : " + chart.size() + " blocs");
        } catch (Exception e) {
            getLogger().severe("Impossible de charger le fichier de chart : " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public GradientManager getGradientManager() {
        return gradientManager;
    }
}
