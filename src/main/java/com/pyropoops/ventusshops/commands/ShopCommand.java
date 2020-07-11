package com.pyropoops.ventusshops.commands;

import com.pyropoops.ventusshops.Shop;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cCorrect Usage - /shop <shop>");
            return true;
        }
        if (!sender.hasPermission("shops." + args[0])) {
            sender.sendMessage("§cYou do not have permission to do that!");
            return true;
        }
        if (!Shop.shops.containsKey(args[0])) {
            sender.sendMessage("§cI could not find a shop with that name!");
            return true;
        }
        Player player = null;
        if (args.length >= 2) {
            player = Bukkit.getPlayer(args[1]);
        } else if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to do this!");
            return true;
        } else {
            player = (Player) sender;
        }
        if (player == null) return true;
        Shop shop = Shop.shops.get(args[0]);
        shop.open(player);
        return true;
    }
}
