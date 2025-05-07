package fr.chiroyuki.chromaBlock.commands.sub;

import fr.chiroyuki.chromaBlock.ChromaBlock;
import fr.chiroyuki.chromaBlock.commands.MessageUtils;
import fr.chiroyuki.chromaBlock.commands.SubCommand;
import fr.chiroyuki.chromaBlock.data.Gradient;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class ListCommand implements SubCommand {
    private final ChromaBlock plugin;

    public ListCommand(ChromaBlock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            MessageUtils.sendError(sender, "Seul un joueur peut utiliser cette commande.");
            return true;
        }

        Map<String, Gradient> gradients = plugin.getGradientManager().listGradients();
        if (gradients.isEmpty()) {
            MessageUtils.sendError(p, "Aucun gradient n'a été sauvegardé.");
            return true;
        }

        p.sendMessage("§6▸ §eGradients disponibles :");
        gradients.forEach((name, g) -> {
            String start = toHex(g.getStartColor());
            String end = toHex(g.getEndColor());
            p.sendMessage(" §8- §b" + name + "§7 : " + start + " → " + end + " (§f" + g.getNumPoints() + " points§7)");
        });
        return true;
    }

    private String toHex(java.awt.Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()).toUpperCase();
    }
}
