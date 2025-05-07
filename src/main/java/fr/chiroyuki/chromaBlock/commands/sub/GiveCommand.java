package fr.chiroyuki.chromaBlock.commands.sub;

import fr.chiroyuki.chromaBlock.ChromaBlock;
import fr.chiroyuki.chromaBlock.commands.MessageUtils;
import fr.chiroyuki.chromaBlock.commands.SubCommand;
import fr.chiroyuki.chromaBlock.data.Gradient;
import fr.chiroyuki.chromaBlock.logic.BlockMatcher;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

public class GiveCommand implements SubCommand {
    private final ChromaBlock plugin;

    public GiveCommand(ChromaBlock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            MessageUtils.sendError(sender, "Seul un joueur peut utiliser cette commande.");
            return true;
        }

        if (args.length != 1) {
            MessageUtils.sendUsage(p, "/gradient give <nom>");
            return true;
        }

        String name = args[0];
        Gradient gradient = plugin.getGradientManager().getGradient(name);
        if (gradient == null) {
            MessageUtils.sendError(p, "Aucun gradient trouvé avec le nom §e" + name + "§c.");
            return true;
        }

        List<Color> colors = gradient.generateColors();
        for (Color color : colors) {
            Material mat = BlockMatcher.getClosestMaterial(color);
            if (mat != null) {
                p.getInventory().addItem(new ItemStack(mat));
            }
        }

        MessageUtils.sendSuccess(p, "Gradient §e" + name + "§a ajouté à votre inventaire.");
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return plugin.getGradientManager().getGradientNames().stream()
                    .filter(name -> name.startsWith(args[0]))
                    .toList();
        }
        return Collections.emptyList();
    }
}
