package com.pyropoops.ventusshops;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Transaction {
    private Economy economy;
    private Player player;
    private ShopItem shopItem;
    private ItemStack itemStack;
    private int amount;
    private boolean buy;

    public Transaction(Player player, ShopItem shopItem, int amount, boolean buy) {
        this.player = player;
        this.shopItem = shopItem;
        this.amount = amount;
        this.itemStack = new ItemStack(shopItem.getMaterial(), amount);
        this.buy = buy;

        if (this.hook()) execute();
    }

    private boolean hook() {
        if (VentusShops.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = VentusShops.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    private boolean inventoryHasItem(Inventory inventory, ItemStack itemStack) {
        int count = 0;
        for (ItemStack i : inventory.getContents()) {
            if (i == null) continue;
            if (i.getType() == itemStack.getType()) count += i.getAmount();
        }
        return count >= itemStack.getAmount();
    }

    private boolean inventoryHasSpace(Inventory inventory, ItemStack itemStack) {
        for (ItemStack i : inventory.getContents()) {
            if (i == null) return true;
            if (i.getType() == Material.AIR) return true;
            if (i.getType() == itemStack.getType()) {
                if (i.getAmount() + itemStack.getAmount() <= 64) return true;
            }
        }
        return false;
    }

    private void buy() {
        if (!this.economy.has(player, this.shopItem.getBuyPrice())) {
            player.sendMessage("§cYou do not have enough money for that!");
            return;
        }
        if (!inventoryHasSpace(player.getInventory(), itemStack)) {
            player.sendMessage("§cYou do not have space in your inventory!");
            return;
        }
        this.economy.withdrawPlayer(player, this.shopItem.getBuyPrice() * this.amount);
        this.player.getInventory().addItem(this.itemStack);
        player.sendMessage("§aTransaction successful!");
    }

    private void sell() {
        if (!inventoryHasItem(player.getInventory(), this.itemStack)) {
            player.sendMessage("§cYou do not have enough items for that!");
            return;
        }
        this.economy.depositPlayer(player, this.shopItem.getSellPrice() * this.amount);
        this.player.getInventory().remove(this.itemStack);
        player.sendMessage("§aTransaction successful!");
    }

    private void execute() {
        if (buy) {
            buy();
        } else {
            sell();
        }
    }

}
