package fr.chiroyuki.chromaBlock.commands;

import org.bukkit.command.CommandSender;
import java.util.List;

public interface SubCommand {

    boolean execute(CommandSender sender, String[] args);

    default List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
