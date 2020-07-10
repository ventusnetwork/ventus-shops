package com.pyropoops.ventusshops;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TestingShop implements CommandExecutor {
    private Shop shop;

    public void init() {
        Shop shop = new Shop("test," ,"Testing Shop", 45);
        for (int i = 0; i < 100; i++) {
            shop.addItem(Material.ACACIA_FENCE, 1, 1);
        }
        shop.createInventories();

        this.shop = shop;

        Objects.requireNonNull(VentusShops.getInstance().getCommand("test")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        shop.open(player);
        return true;
    }
}
