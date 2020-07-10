package com.pyropoops.ventusshops;

import com.pyropoops.ventusshops.commands.ShopCommand;
import com.pyropoops.ventusshops.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Set;

public final class VentusShops extends JavaPlugin {
    private static VentusShops instance;
    private Config shopsConfig;

    public static VentusShops getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Objects.requireNonNull(getCommand("shop")).setExecutor(new ShopCommand());

        shopsConfig = new Config("shops.yml", null);
        this.registerShops();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
    }

    private void registerShops() {
        Set<String> shops = shopsConfig.getConfig().getKeys(false);
        for (String shopString : shops) {
            ConfigurationSection section = shopsConfig.getConfig().getConfigurationSection(shopString);
            if (section == null) continue;
            Set<String> items = section.getKeys(false);
            String display = section.getString("display");
            int size = 45;
            if (display == null) display = shopString;
            if (section.contains("size")) size = section.getInt("size");
            Shop shop = new Shop(shopString,
                    ChatColor.translateAlternateColorCodes('&', display),
                    size);
            for (String s : items) {
                ConfigurationSection itemSection = section.getConfigurationSection(s);
                if (itemSection == null) continue;
                if (!itemSection.contains("buy-price")
                        || !itemSection.contains("sell-price")) continue;
                Material material = Material.getMaterial(s);
                if (material == null) continue;
                double buyPrice = itemSection.getDouble("buy-price");
                double sellPrice = itemSection.getDouble("sell-price");

                shop.addItem(material, buyPrice, sellPrice);
            }
            shop.createInventories();
        }
    }

    public void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
