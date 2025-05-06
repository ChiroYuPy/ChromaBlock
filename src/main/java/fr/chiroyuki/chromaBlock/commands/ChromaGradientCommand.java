package fr.chiroyuki.chromaBlock.commands;

import fr.chiroyuki.chromaBlock.BlockMatcher;
import fr.chiroyuki.chromaBlock.ColorInterpolator;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.Color;
import java.util.List;

public class ChromaGradientCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("Cette commande doit être utilisée par un joueur.");
            return true;
        }

        GradientArgs parsedArgs = parseArguments(args, p);
        if (parsedArgs == null) return true;

        List<Color> colors = ColorInterpolator.interpolateColors(
                parsedArgs.startColor,
                parsedArgs.endColor,
                parsedArgs.count,
                parsedArgs.easing
        );

        for (Color color : colors) {
            Material mat = BlockMatcher.getClosestMaterial(color);
            if (mat != null) {
                p.getInventory().addItem(new ItemStack(mat, 1));
            }
        }

        p.sendMessage("§aVous avez reçu " + parsedArgs.count + " blocs en dégradé.");
        return true;
    }

    private GradientArgs parseArguments(String[] args, Player p) {
        if (args.length < 3) {
            p.sendMessage("§cUsage: /chromagradient <start> <end> <count> [easing]");
            return null;
        }

        Color startColor, endColor;
        int count;
        ColorInterpolator.Easing easing = ColorInterpolator.Easing.LINEAR;

        try {
            startColor = Color.decode(args[0]);
        } catch (NumberFormatException e) {
            p.sendMessage("§cCouleur de début invalide : " + args[0]);
            return null;
        }

        try {
            endColor = Color.decode(args[1]);
        } catch (NumberFormatException e) {
            p.sendMessage("§cCouleur de fin invalide : " + args[1]);
            return null;
        }

        try {
            count = Integer.parseInt(args[2]);
            if (count < 2) {
                p.sendMessage("§cLe nombre de blocs doit être ≥ 2.");
                return null;
            }
        } catch (NumberFormatException e) {
            p.sendMessage("§cNombre de blocs invalide : " + args[2]);
            return null;
        }

        if (args.length >= 4) {
            try {
                easing = ColorInterpolator.Easing.valueOf(args[3].toUpperCase());
            } catch (IllegalArgumentException e) {
                p.sendMessage("§cFonction de gradient inconnue : " + args[3]);
                return null;
            }
        }

        return new GradientArgs(startColor, endColor, count, easing);
    }

    private static class GradientArgs {
        public final Color startColor;
        public final Color endColor;
        public final int count;
        public final ColorInterpolator.Easing easing;

        public GradientArgs(Color startColor, Color endColor, int count, ColorInterpolator.Easing easing) {
            this.startColor = startColor;
            this.endColor = endColor;
            this.count = count;
            this.easing = easing;
        }
    }
}
