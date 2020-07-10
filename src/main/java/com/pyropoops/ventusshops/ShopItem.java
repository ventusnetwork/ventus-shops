package com.pyropoops.ventusshops;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ShopItem {
    private final Material material;
    private final double buyPrice;
    private final double sellPrice;

    public ShopItem(Material material, double buyPrice, double sellPrice) {
        this.material = material;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public Material getMaterial() {
        return material;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public ItemStack getDisplayItem() {
        ItemStack i = new ItemStack(this.material, 1);
        ItemMeta meta = i.getItemMeta();
        if (meta == null) return i;
        meta.setLore(Arrays.asList("§aBuy: " + this.getBuyPrice(), "§cSell: " + this.getSellPrice()));
        i.setItemMeta(meta);
        return i;
    }

    public ItemStack getDisplayItem(int amount) {
        ItemStack i = new ItemStack(this.material, amount);
        ItemMeta meta = i.getItemMeta();
        if (meta == null) return i;
        meta.setLore(Arrays.asList("§aBuy: " + this.getBuyPrice() * amount, "§cSell: " + this.getSellPrice() * amount));
        i.setItemMeta(meta);
        return i;
    }
}
