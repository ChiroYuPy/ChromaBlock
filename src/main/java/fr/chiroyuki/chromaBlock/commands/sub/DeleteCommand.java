package fr.chiroyuki.chromaBlock.commands.sub;

import fr.chiroyuki.chromaBlock.ChromaBlock;
import fr.chiroyuki.chromaBlock.commands.MessageUtils;
import fr.chiroyuki.chromaBlock.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class DeleteCommand implements SubCommand {
    private final ChromaBlock plugin;

    public DeleteCommand(ChromaBlock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            MessageUtils.sendError(sender, "Seul un joueur peut utiliser cette commande.");
            return true;
        }

        if (args.length != 1) {
            MessageUtils.sendUsage(p, "/gradient delete <nom>");
            return true;
        }

        String name = args[0];
        if (!plugin.getGradientManager().exists(name)) {
            MessageUtils.sendError(p, "Aucun gradient nommé §e" + name + "§c n'existe.");
            return true;
        }

        plugin.getGradientManager().deleteGradient(name);
        MessageUtils.sendSuccess(p, "Gradient §e" + name + "§a supprimé.");
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
