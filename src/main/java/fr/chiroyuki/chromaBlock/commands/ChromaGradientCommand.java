package fr.chiroyuki.chromaBlock.commands;

import fr.chiroyuki.chromaBlock.ChromaBlock;
import fr.chiroyuki.chromaBlock.commands.sub.DeleteCommand;
import fr.chiroyuki.chromaBlock.commands.sub.GiveCommand;
import fr.chiroyuki.chromaBlock.commands.sub.ListCommand;
import fr.chiroyuki.chromaBlock.commands.sub.SaveCommand;
import org.bukkit.command.*;
import java.util.*;

public class ChromaGradientCommand implements CommandExecutor, TabCompleter {
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public ChromaGradientCommand(ChromaBlock plugin) {
        subCommands.put("save", new SaveCommand(plugin));
        subCommands.put("delete", new DeleteCommand(plugin));
        subCommands.put("list", new ListCommand(plugin));
        subCommands.put("give", new GiveCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            MessageUtils.sendError(sender, "Usage : /gradient <save|delete|list|give> [...]");
            return false;
        }

        SubCommand sub = subCommands.get(args[0].toLowerCase());
        if (sub == null) {
            MessageUtils.sendError(sender, "Sous-commande inconnue.");
            return false;
        }

        return sub.execute(sender, Arrays.copyOfRange(args, 1, args.length));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return subCommands.keySet().stream()
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .toList();
        }

        SubCommand sub = subCommands.get(args[0].toLowerCase());
        return sub != null ? sub.tabComplete(sender, Arrays.copyOfRange(args, 1, args.length)) : Collections.emptyList();
    }
}
