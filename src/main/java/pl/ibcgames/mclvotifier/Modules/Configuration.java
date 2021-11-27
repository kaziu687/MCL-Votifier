package pl.ibcgames.mclvotifier.Modules;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import pl.ibcgames.mclvotifier.Votifier;

import java.io.File;

public class Configuration {
    private Votifier plugin;

    public Configuration(Votifier plugin ) {
        this.plugin = plugin;
        this.reload();
    }

    public void reload() {
        if(!new File( this.plugin.getDataFolder(), "config.yml").exists()) {
            this.plugin.warning("Configuration file not found. Generating new...");
            this.plugin.saveDefaultConfig();
        }

        this.plugin.reloadConfig();
    }

    public ConfigurationSection get(String s ) {
        return this.plugin.getConfig().getConfigurationSection( s );
    }
    public FileConfiguration get() {
        return this.plugin.getConfig();
    }
}