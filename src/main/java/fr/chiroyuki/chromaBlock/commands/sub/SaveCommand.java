package fr.chiroyuki.chromaBlock.commands.sub;

import fr.chiroyuki.chromaBlock.ChromaBlock;
import fr.chiroyuki.chromaBlock.commands.MessageUtils;
import fr.chiroyuki.chromaBlock.commands.SubCommand;
import fr.chiroyuki.chromaBlock.data.Gradient;
import fr.chiroyuki.chromaBlock.logic.ColorInterpolator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

public class SaveCommand implements SubCommand {
    private final ChromaBlock plugin;

    public SaveCommand(ChromaBlock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            MessageUtils.sendError(sender, "Seul un joueur peut utiliser cette commande.");
            return true;
        }

        if (args.length != 4) {
            MessageUtils.sendUsage(p, "/gradient save <nom> <couleur_debut> <couleur_fin> <nombre>");
            return true;
        }

        String name = args[0];
        String startColorStr = args[1];
        String endColorStr = args[2];
        int count;

        try {
            // Vérification de la validité du format hexadécimal
            if (!startColorStr.startsWith("#") || startColorStr.length() != 7) {
                MessageUtils.sendError(p, "Erreur : Le format de la couleur de départ est invalide. Utilisez #RRGGBB.");
                return true;
            }
            if (!endColorStr.startsWith("#") || endColorStr.length() != 7) {
                MessageUtils.sendError(p, "Erreur : Le format de la couleur de fin est invalide. Utilisez #RRGGBB.");
                return true;
            }

            Color start = Color.decode(startColorStr);
            Color end = Color.decode(endColorStr);

            count = Integer.parseInt(args[3]);
            if (count < 2) {
                MessageUtils.sendError(p, "Le nombre de points doit être ≥ 2.");
                return true;
            }
            if (count > 1000) { // Exemple de limite supérieure pour éviter un nombre excessif de points
                MessageUtils.sendError(p, "Le nombre de points est trop élevé. Veuillez entrer un nombre raisonnable.");
                return true;
            }

            Gradient gradient = new Gradient(start, end, count, ColorInterpolator.Easing.LINEAR);
            if (!plugin.getGradientManager().addGradient(name, gradient)) {
                MessageUtils.sendError(p, "Un gradient avec ce nom existe déjà.");
                return true;
            }

            MessageUtils.sendSuccess(p, "Le gradient §e" + name + "§a a été sauvegardé avec succès !");
            return true;
        } catch (NumberFormatException e) {
            MessageUtils.sendError(p, "Erreur : Le nombre de points doit être un entier valide.");
        } catch (IllegalArgumentException e) {
            MessageUtils.sendError(p, "Erreur : Format de couleur invalide. Utilisez #RRGGBB.");
        }

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1 -> {
                return plugin.getGradientManager().getGradientNames().stream()
                        .filter(name -> name.startsWith(args[0]))
                        .toList();
            }
            case 2, 3 -> {
                return List.of("#FF0000", "#00FF00", "#0000FF", "#FFFFFF", "#000000").stream()
                        .filter(color -> color.startsWith(args[args.length - 1].toUpperCase()))
                        .toList();
            }
            case 4 -> {
                return List.of("2", "3", "5", "10", "20", "50", "100").stream()
                        .filter(n -> n.startsWith(args[3]))
                        .toList();
            }
            default -> {
                return Collections.emptyList();
            }
        }
    }

}
