package fr.chiroyuki.chromaBlock.commands;

import fr.chiroyuki.chromaBlock.ChromaBlock;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageUtils {
    private static ChromaBlock plugin;

    public static void init(ChromaBlock chromaBlock) {
        plugin = chromaBlock;
    }

    public static void sendError(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.RED + "⛔ " + ChatColor.GRAY + msg);
        plugin.getLogger().warning(msg);
    }

    public static void sendSuccess(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.GREEN + "✅ " + ChatColor.GRAY + msg);
        plugin.getLogger().info(msg);
    }

    public static void sendUsage(CommandSender sender, String usage) {
        sender.sendMessage(ChatColor.YELLOW + "Usage : " + usage);
    }
}
