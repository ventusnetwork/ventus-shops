package com.pyropoops.ventusshops.config;

import com.google.common.base.Charsets;
import com.pyropoops.ventusshops.VentusShops;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config {
    private File file;
    private FileConfiguration config;

    private String name;
    private String folder;
    private VentusShops plugin;

    public Config(String name, String folder) {
        if (!name.toLowerCase().endsWith(".yml")) {
            name += ".yml";
        }

        this.name = name;
        this.folder = folder;
        this.plugin = VentusShops.getInstance();

        loadConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        try {
            file = folder != null ? new File(plugin.getDataFolder() + "/" + folder + "/" + name) : new File(plugin.getDataFolder() + "/" + name);
            config = YamlConfiguration.loadConfiguration(file);
            InputStream defConfigStream = folder != null ? plugin.getResource(folder + "/" + name) : plugin.getResource(name);
            if (!file.exists()) {
                if (folder != null) {
                    plugin.saveResource(folder + "/" + name, false);
                } else {
                    plugin.saveResource(name, false);
                }
            }
            if (defConfigStream != null) {
                config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
                config.options().copyDefaults(true);
            }
            saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
