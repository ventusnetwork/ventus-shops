package com.pyropoops.ventusshops;

import com.pyropoops.ventusshops.commands.ShopCommand;
import com.pyropoops.ventusshops.config.Config;
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
        for (String shop : shops) {
            ConfigurationSection section = shopsConfig.getConfig().getConfigurationSection(shop);
            if(section == null) continue;
            String item = section.getString("item");
            if(item == null) continue;

        }
    }

    public void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
