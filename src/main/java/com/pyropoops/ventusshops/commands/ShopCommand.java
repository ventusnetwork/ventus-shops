package com.pyropoops.ventusshops.commands;

import com.pyropoops.ventusshops.Shop;
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
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cYou must be a player to do this!");
            return true;
        }
        Shop shop = Shop.shops.get(args[0]);
        shop.open((Player) sender);
        return true;
    }
}
