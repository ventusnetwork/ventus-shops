package com.pyropoops.ventusshops;

import com.pyropoops.ventusshops.commands.ShopCommand;
import org.bukkit.command.Command;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class VentusShops extends JavaPlugin {
    private static VentusShops instance;

    public static VentusShops getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Objects.requireNonNull(getCommand("shop")).setExecutor(new ShopCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
    }

    public void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
