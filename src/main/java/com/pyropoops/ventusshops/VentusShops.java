package com.pyropoops.ventusshops;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class VentusShops extends JavaPlugin {
    private static VentusShops instance;

    public static VentusShops getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        TestingShop testingShop = new TestingShop();
        testingShop.init();
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
